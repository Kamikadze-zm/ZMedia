package ru.kamikadze_zm.zmedia.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kamikadze_zm.zmedia.model.dto.SearchResult;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.repository.PublicationRepository;
import ru.kamikadze_zm.zmedia.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    private static final int NAME_MIN_LENGTH = 3;

    @Autowired
    List<PublicationRepository<? extends Publication>> repositories;

    @Override
    @Transactional(readOnly = true)
    public List<SearchResult> searchByName(String name) {
        if (name == null || name.length() < NAME_MIN_LENGTH) {
            return Collections.emptyList();
        }
        String lowerName = name.toLowerCase();
        List<Publication> publications = new ArrayList<>();
        repositories.stream().forEach(r -> publications.addAll(r.searchByName(lowerName)));
        publications.sort(Publication::compareByDescPublishDateTo);
        List<SearchResult> views = new ArrayList<>(publications.size());
        publications.stream().forEach(p -> views.add(new SearchResult(p)));
        return views;
    }
}
