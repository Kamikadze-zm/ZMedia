package ru.kamikadze_zm.zmedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kamikadze_zm.zmedia.model.dto.CommentDTO;
import ru.kamikadze_zm.zmedia.model.entity.Game;
import ru.kamikadze_zm.zmedia.model.entity.GameComment;
import ru.kamikadze_zm.zmedia.repository.GameCommentRepository;
import ru.kamikadze_zm.zmedia.service.GameCommentService;
import ru.kamikadze_zm.zmedia.service.GameService;

@Service
public class GameCommentServiceImpl extends CommentServiceImpl<Game, GameComment> implements GameCommentService {

    @Autowired
    public GameCommentServiceImpl(GameCommentRepository gameCommentRepository, GameService gameService) {
        super(gameCommentRepository, gameService);
    }

    @Override
    protected GameComment mapDtoToEntity(CommentDTO dto) {
        GameComment c = new GameComment();
        mapDtoToEntity(dto, c);
        return c;
    }

    @Override
    protected void mapDtoToEntity(CommentDTO dto, GameComment entity) {
        mapDtoToComment(dto, entity);
    }
}
