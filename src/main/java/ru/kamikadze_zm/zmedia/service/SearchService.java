package ru.kamikadze_zm.zmedia.service;

import java.util.List;
import ru.kamikadze_zm.zmedia.model.dto.SearchResult;

public interface SearchService {

    List<SearchResult> searchByName(String name);
}
