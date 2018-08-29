package ru.kamikadze_zm.zmedia.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kamikadze_zm.zmedia.model.dto.NoveltyDTO;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.repository.NoveltiesRepository;
import ru.kamikadze_zm.zmedia.service.NoveltiesService;

@Service
public class NoveltiesServiceImpl implements NoveltiesService {

    private static final int DEFAULT_NUMBER_NOVELTIES = 20;

    @Autowired
    private NoveltiesRepository noveltiesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NoveltyDTO> getNovelties() {
        List<Publication> publications = noveltiesRepository.getNovelties(DEFAULT_NUMBER_NOVELTIES);
        List<NoveltyDTO> novelties = new ArrayList<>();
        publications.forEach(p -> novelties.add(new NoveltyDTO(p)));
        return novelties;
    }
}
