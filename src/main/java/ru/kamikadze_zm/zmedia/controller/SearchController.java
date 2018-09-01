package ru.kamikadze_zm.zmedia.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kamikadze_zm.zmedia.model.dto.SearchResult;
import ru.kamikadze_zm.zmedia.service.SearchService;

@RestController
@RequestMapping("/api/search/")
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping(path = "byname/{name}")
    public List<SearchResult> searchByName(@PathVariable("name") String name) {
        return searchService.searchByName(name);
    }
}
