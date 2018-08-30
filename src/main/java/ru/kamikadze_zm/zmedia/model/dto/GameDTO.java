package ru.kamikadze_zm.zmedia.model.dto;

import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.Game.GameGenre;

public class GameDTO extends PublicationDTO<GameGenre> {

    public GameDTO() {
    }

    public GameDTO(String header,
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
            List<DownloadLinkDTO> downloadLinks) {
        super(header, note, name, originalName, genres, releaseYear, coverLink, fileSize, description, additionalInfo, details, downloadLinks);
    }

    @Override
    public String toString() {
        return "GameDTO{" + super.toString() + '}';
    }
}
