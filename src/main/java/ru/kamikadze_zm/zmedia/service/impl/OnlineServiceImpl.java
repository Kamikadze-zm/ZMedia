package ru.kamikadze_zm.zmedia.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kamikadze_zm.zmedia.model.StreamInfo;
import ru.kamikadze_zm.zmedia.service.OnlineService;

@Service
public class OnlineServiceImpl implements OnlineService {

    @Value("${online.host}")
    private String host;
    @Value("${online.stream-info-part}")
    private String streamInfoPart;

    @Override
    public StreamInfo getStreamInfo() {
        RestTemplate rt = new RestTemplate();
        return rt.getForEntity(host + streamInfoPart, StreamInfo.class).getBody();
    }
}
