package ru.kamikadze_zm.zmedia.model.dto;

import java.util.Date;
import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.Game;
import ru.kamikadze_zm.zmedia.model.entity.Game.GameGenre;
import ru.kamikadze_zm.zmedia.model.entity.GameDownloadLink;

public class GameViewDTO extends PublicationViewDTO<GameGenre> {

    public GameViewDTO(Integer id,
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
            String details,
            List<GameDownloadLink> downloadLinks,
            Date publishDate,
            String author,
            int viewsCount,
            int commentsCount) {
        super(id, header, note, name, originalName, genres, releaseYear, coverLink, fileSize,
                description, additionalInfo, details, downloadLinks, publishDate, author, viewsCount, commentsCount);
    }

    public GameViewDTO(Game game) {
        super(game);
    }

    @Override
    public String toString() {
        return "GameViewDTO{" + super.toString() + '}';
    }
}
