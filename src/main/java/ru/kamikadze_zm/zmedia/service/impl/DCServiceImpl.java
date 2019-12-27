package ru.kamikadze_zm.zmedia.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.kamikadze_zm.zmedia.model.entity.DownloadLink;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.model.entity.util.PublicationType;
import ru.kamikadze_zm.zmedia.service.DCService;
import ru.kamikadze_zm.zmedia.util.MagnetsUtil;

@Service
public class DCServiceImpl implements DCService {

    private static final Logger LOG = LogManager.getLogger(DCServiceImpl.class);

    @Value("${dc.magnets-file}")
    private String magnetsFile;
    @Value("${dc.magnets-file-encoding}")
    private String magnetsFileEncoding;
    @Value("${dc.max-magnets}")
    private int maxMagnets;

    @Value("${host}")
    private String host;
    @Value("${film-part}")
    private String filmPart;
    @Value("${tv_series-part}")
    private String tvSeriesPart;
    @Value("${game-part}")
    private String gamePart;

    @Autowired
    private MagnetsUtil magnetsUtil;

    @Async
    @Override
    public void updateMagnets(Publication<?, ?, DownloadLink> p) {
        File file = new File(magnetsFile);
        if (!file.exists()) {
            return;
        }
        Charset charset = Charset.forName(magnetsFileEncoding);
        List<String> message = new ArrayList<>();
        LinkedHashMap<String, String> magnets = new LinkedHashMap<>((int) (maxMagnets * 1.5));

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String hash = magnetsUtil.getHash(line);
                if (hash != null) {
                    magnets.put(hash, line);
                } else {
                    message.add(line);
                }
            }
        } catch (IOException e) {
            LOG.warn("Cannot read magnets: ", e);
        }

        PublicationType type = PublicationType.identifyType(p);
        String part = "";
        switch (type) {
            case FILM:
                part = filmPart;
                break;
            case TV_SERIES:
                part = tvSeriesPart;
                break;
            case GAME:
                part = gamePart;
                break;
        }
        String url = host + part + p.getId();
        String magnet = p.getDownloadLinks().get(0).getLink();
        String line = magnet + "    " + url;

        magnets.put(magnetsUtil.getHash(magnet), line);
        List<String> magnetsList = new ArrayList<>(magnets.values());
        if (magnetsList.size() > maxMagnets) {
            magnetsList = magnetsList.subList(magnetsList.size() - maxMagnets, magnetsList.size());
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset))) {
            for (String messageLine : message) {
                bw.write(messageLine);
                bw.newLine();
            }
            bw.newLine();
            for (String m : magnetsList) {
                bw.write(m);
                bw.newLine();
            }
        } catch (IOException e) {
            LOG.warn("Cannot write magnets: ", e);
        }
    }
}
