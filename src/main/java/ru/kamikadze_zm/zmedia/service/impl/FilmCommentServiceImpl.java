package ru.kamikadze_zm.zmedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kamikadze_zm.zmedia.model.dto.CommentDTO;
import ru.kamikadze_zm.zmedia.model.entity.Film;
import ru.kamikadze_zm.zmedia.model.entity.FilmComment;
import ru.kamikadze_zm.zmedia.repository.FilmCommentRepository;
import ru.kamikadze_zm.zmedia.service.FilmCommentService;
import ru.kamikadze_zm.zmedia.service.FilmService;

@Service
public class FilmCommentServiceImpl extends CommentServiceImpl<Film, FilmComment> implements FilmCommentService {

    @Autowired
    public FilmCommentServiceImpl(FilmCommentRepository filmCommentRepository, FilmService filmService) {
        super(filmCommentRepository, filmService);
    }

    @Override
    protected FilmComment mapDtoToEntity(CommentDTO dto) {
        FilmComment c = new FilmComment();
        mapDtoToEntity(dto, c);
        return c;
    }

    @Override
    protected void mapDtoToEntity(CommentDTO dto, FilmComment entity) {
        mapDtoToComment(dto, entity);
    }
}
