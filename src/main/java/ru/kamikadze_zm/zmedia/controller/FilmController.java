package ru.kamikadze_zm.zmedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kamikadze_zm.zmedia.model.dto.FilmDTO;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmGenre;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmQuality;
import ru.kamikadze_zm.zmedia.service.FilmCommentService;
import ru.kamikadze_zm.zmedia.service.FilmService;

@RestController
@RequestMapping("/api/films/")
public class FilmController extends PublicationController<FilmService, FilmDTO, FilmCommentService> {

    @Autowired
    public FilmController(FilmService filmService, FilmCommentService filmCommentService) {
        super(filmService, filmCommentService);
    }

    @RequestMapping("genres")
    public FilmGenre[] filmGenres() {
        return FilmGenre.values();
    }

    @RequestMapping("qualities")
    public FilmQuality[] filmQualities() {
        return FilmQuality.values();
    }
}
