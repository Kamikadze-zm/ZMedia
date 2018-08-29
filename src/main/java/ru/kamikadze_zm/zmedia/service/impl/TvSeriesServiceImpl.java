package ru.kamikadze_zm.zmedia.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kamikadze_zm.zmedia.model.dto.DownloadLinkDTO;
import ru.kamikadze_zm.zmedia.model.dto.TvSeriesDTO;
import ru.kamikadze_zm.zmedia.model.dto.TvSeriesShortViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.TvSeriesViewDTO;
import ru.kamikadze_zm.zmedia.model.entity.DownloadLink;
import ru.kamikadze_zm.zmedia.model.entity.TvSeries;
import ru.kamikadze_zm.zmedia.model.entity.TvSeriesDownloadLink;
import ru.kamikadze_zm.zmedia.repository.TvSeriesRepository;
import ru.kamikadze_zm.zmedia.service.TvSeriesService;

@Service
public class TvSeriesServiceImpl extends PublicationServiceImpl<TvSeries, TvSeriesDTO, TvSeriesShortViewDTO, TvSeriesViewDTO>
        implements TvSeriesService {

    @Autowired
    public TvSeriesServiceImpl(TvSeriesRepository tvSeriesRepository) {
        super(tvSeriesRepository);
    }

    @Override
    protected TvSeriesShortViewDTO mapPublicationToShortView(TvSeries publication) {
        return new TvSeriesShortViewDTO(publication);
    }

    @Override
    protected TvSeriesViewDTO mapPublicationToFullView(TvSeries publication) {
        return new TvSeriesViewDTO(publication);
    }

    @Override
    protected TvSeries mapDtoToEntity(TvSeriesDTO dto) {
        TvSeries t = new TvSeries();
        mapDtoToEntity(dto, t);
        return t;
    }

    @Override
    protected void mapDtoToEntity(TvSeriesDTO dto, TvSeries entity) {
        mapDtoToPublication(dto, entity);
        entity.setSeasonNumber(dto.getSeasonNumber());
        entity.setQuality(dto.getQuality());
    }

    @Override
    protected void mapDownloadLinks(List<? super DownloadLink> entityLinks, List<DownloadLinkDTO> dtoLinks) {
        dtoLinks.forEach(l -> entityLinks.add(new TvSeriesDownloadLink(l.getDescription(), l.getLink())));
    }
}
