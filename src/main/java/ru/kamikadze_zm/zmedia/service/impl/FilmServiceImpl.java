package ru.kamikadze_zm.zmedia.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kamikadze_zm.zmedia.model.dto.DownloadLinkDTO;
import ru.kamikadze_zm.zmedia.model.dto.FilmDTO;
import ru.kamikadze_zm.zmedia.model.dto.FilmShortViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.FilmViewDTO;
import ru.kamikadze_zm.zmedia.model.entity.DownloadLink;
import ru.kamikadze_zm.zmedia.model.entity.Film;
import ru.kamikadze_zm.zmedia.model.entity.FilmDownloadLink;
import ru.kamikadze_zm.zmedia.repository.FilmRepository;
import ru.kamikadze_zm.zmedia.service.FilmService;

@Service
public class FilmServiceImpl extends PublicationServiceImpl<Film, FilmDTO, FilmShortViewDTO, FilmViewDTO> implements FilmService {

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository) {
        super(filmRepository);
    }

    @Override
    protected FilmShortViewDTO mapPublicationToShortView(Film publication) {
        return new FilmShortViewDTO(publication);
    }

    @Override
    protected FilmViewDTO mapPublicationToFullView(Film publication) {
        return new FilmViewDTO(publication);
    }

    @Override
    protected Film mapDtoToEntity(FilmDTO dto) {
        Film f = new Film();
        mapDtoToEntity(dto, f);
        return f;
    }

    @Override
    protected void mapDtoToEntity(FilmDTO dto, Film entity) {
        mapDtoToPublication(dto, entity);
        entity.setQuality(dto.getQuality());
    }

    @Override
    protected void mapDownloadLinks(List<? super DownloadLink> entityLinks, List<DownloadLinkDTO> dtoLinks) {
        dtoLinks.forEach(l -> entityLinks.add(new FilmDownloadLink(l.getDescription(), l.getLink())));
    }
}
