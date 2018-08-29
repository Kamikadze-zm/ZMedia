package ru.kamikadze_zm.zmedia.model.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmGenre;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmQuality;

public class FilmDTO extends PublicationDTO<FilmGenre> {

    @NotNull(message = "Выберите качество")
    private FilmQuality quality;

    public FilmDTO() {
    }

    public FilmDTO(String header,
            String note,
            String name,
            String originalName,
            List<FilmGenre> genres,
            Short releaseYear,
            String coverLink,
            Integer fileSize,
            String description,
            String additionalInfo,
            String details,
            List<DownloadLinkDTO> downloadLinks,
            FilmQuality quality) {
        super(header, note, name, originalName, genres, releaseYear, coverLink, fileSize, description, additionalInfo, details, downloadLinks);
        this.quality = quality;
    }

    public FilmQuality getQuality() {
        return quality;
    }

    public void setQuality(FilmQuality quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "FilmDTO{"
                + "header=" + header
                + ", note=" + note
                + ", name=" + name
                + ", originalName=" + originalName
                + ", genres=" + genres
                + ", quality=" + quality.getQuality()
                + ", releaseYear=" + releaseYear
                + ", coverLink=" + coverLink
                + ", fileSize=" + fileSize
                + ", description=" + description
                + ", additionalInfo=" + additionalInfo
                + ", details=" + details
                + ", downloadLinks=" + downloadLinks.toString() + '}';
    }
}
