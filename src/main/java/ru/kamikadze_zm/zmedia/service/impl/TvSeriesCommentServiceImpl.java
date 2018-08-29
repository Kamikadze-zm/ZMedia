package ru.kamikadze_zm.zmedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kamikadze_zm.zmedia.model.dto.CommentDTO;
import ru.kamikadze_zm.zmedia.model.entity.TvSeries;
import ru.kamikadze_zm.zmedia.model.entity.TvSeriesComment;
import ru.kamikadze_zm.zmedia.repository.TvSeriesCommentRepository;
import ru.kamikadze_zm.zmedia.service.TvSeriesCommentService;
import ru.kamikadze_zm.zmedia.service.TvSeriesService;

@Service
public class TvSeriesCommentServiceImpl extends CommentServiceImpl<TvSeries, TvSeriesComment> implements TvSeriesCommentService {

    @Autowired
    public TvSeriesCommentServiceImpl(TvSeriesCommentRepository tvSeriesCommentRepository, TvSeriesService tvSeriesService) {
        super(tvSeriesCommentRepository, tvSeriesService);
    }

    @Override
    protected TvSeriesComment mapDtoToEntity(CommentDTO dto) {
        TvSeriesComment c = new TvSeriesComment();
        mapDtoToEntity(dto, c);
        return c;
    }

    @Override
    protected void mapDtoToEntity(CommentDTO dto, TvSeriesComment entity) {
        mapDtoToComment(dto, entity);
    }
}
