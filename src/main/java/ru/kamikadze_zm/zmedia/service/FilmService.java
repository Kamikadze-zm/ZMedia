package ru.kamikadze_zm.zmedia.service;

import ru.kamikadze_zm.zmedia.model.dto.FilmDTO;
import ru.kamikadze_zm.zmedia.model.dto.FilmShortViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.FilmViewDTO;
import ru.kamikadze_zm.zmedia.model.entity.Film;

public interface FilmService extends PublicationService<Film, FilmDTO, FilmShortViewDTO, FilmViewDTO> {

}
