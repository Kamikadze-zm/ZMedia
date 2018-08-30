package ru.kamikadze_zm.zmedia.model.entity.util;

import ru.kamikadze_zm.zmedia.model.entity.Game.GameGenre;

public class GameGenreSerializer extends GenreSerializer<GameGenre> {

    public GameGenreSerializer() {
        super(GameGenre.class);
    }
}
