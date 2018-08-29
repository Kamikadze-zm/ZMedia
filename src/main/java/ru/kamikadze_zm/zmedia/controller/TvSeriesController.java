package ru.kamikadze_zm.zmedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kamikadze_zm.zmedia.model.dto.TvSeriesDTO;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmGenre;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmQuality;
import ru.kamikadze_zm.zmedia.service.TvSeriesCommentService;
import ru.kamikadze_zm.zmedia.service.TvSeriesService;

@RestController
@RequestMapping("/api/tvseries/")
public class TvSeriesController extends PublicationController<TvSeriesService, TvSeriesDTO, TvSeriesCommentService> {

    @Autowired
    public TvSeriesController(TvSeriesService tvSeriesService, TvSeriesCommentService tvSeriesCommentService) {
        super(tvSeriesService, tvSeriesCommentService);
    }

    @RequestMapping("genres")
    public FilmGenre[] tvSeriesGenres() {
        return FilmGenre.values();
    }

    @RequestMapping("qualities")
    public FilmQuality[] tvSeriesQualities() {
        return FilmQuality.values();
    }
}
