package ru.kamikadze_zm.zmedia.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kamikadze_zm.zmedia.model.dto.NoveltyDTO;
import ru.kamikadze_zm.zmedia.service.NoveltiesService;

@RestController
public class NoveltiesController {

    @Autowired
    private NoveltiesService noveltiesService;

    @GetMapping("/api/novelties")
    public List<NoveltyDTO> getNovelties() {
        return noveltiesService.getNovelties();
    }
}
