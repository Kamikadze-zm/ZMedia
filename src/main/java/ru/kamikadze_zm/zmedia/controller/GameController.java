package ru.kamikadze_zm.zmedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kamikadze_zm.zmedia.model.dto.GameDTO;
import ru.kamikadze_zm.zmedia.model.entity.Game.GameGenre;
import ru.kamikadze_zm.zmedia.service.GameCommentService;
import ru.kamikadze_zm.zmedia.service.GameService;

@RestController
@RequestMapping("/api/games/")
public class GameController extends PublicationController<GameService, GameDTO, GameCommentService> {

    @Autowired
    public GameController(GameService gameService, GameCommentService gameCommentService) {
        super(gameService, gameCommentService);
    }

    @RequestMapping("genres")
    public GameGenre[] gameGenres() {
        return GameGenre.values();
    }
}
