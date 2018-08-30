package ru.kamikadze_zm.zmedia.model.entity.util;

import ru.kamikadze_zm.zmedia.model.entity.Film;
import ru.kamikadze_zm.zmedia.model.entity.Game;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.model.entity.TvSeries;

public enum PublicationType {
    FILM,
    TV_SERIES,
    GAME;

    public static PublicationType identifyType(Publication p) {
        if (p instanceof Film) {
            return FILM;
        }
        if (p instanceof TvSeries) {
            return TV_SERIES;
        }
        if (p instanceof Game) {
            return GAME;
        }
        throw new RuntimeException("Unknown publication type: " + p.getClass().getSimpleName());
    }
}
