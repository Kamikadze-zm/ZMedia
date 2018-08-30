package ru.kamikadze_zm.zmedia.model.dto;

import java.util.Date;
import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.Game;
import ru.kamikadze_zm.zmedia.model.entity.Game.GameGenre;

public class GameShortViewDTO extends PublicationShortViewDTO<GameGenre> {

    public GameShortViewDTO(Integer id,
            String header,
            String note,
            String name,
            String originalName,
            List<GameGenre> genres,
            Short releaseYear,
            String coverLink,
            Integer fileSize,
            String description,
            String additionalInfo,
            Date publishDate,
            String author,
            int viewsCount,
            int commentsCount) {
        super(id, header, note, name, originalName, genres, releaseYear, coverLink, fileSize,
                description, additionalInfo, publishDate, author, viewsCount, commentsCount);
    }

    public GameShortViewDTO(Game game) {
        super(game);
    }

    @Override
    public String toString() {
        return "GameShortViewDTO{" + super.toString() + '}';
    }
}
