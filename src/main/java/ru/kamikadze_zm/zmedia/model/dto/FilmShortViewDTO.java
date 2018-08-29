package ru.kamikadze_zm.zmedia.model.dto;

import java.util.Date;
import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.Film;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmGenre;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmQuality;

public class FilmShortViewDTO extends PublicationShortViewDTO<FilmGenre> {

    private final FilmQuality quality;

    public FilmShortViewDTO(
            Integer id,
            String header,
            String note,
            String name,
            String originalName,
            List<FilmGenre> genres,
            Short releaseYear,
            String coverLink,
            Integer fileSize,
            String description,
            String additionalInfo,
            Date publishDate,
            String author,
            int viewsCount,
            int commentsCount,
            FilmQuality quality) {
        super(id, header, note, name, originalName, genres, releaseYear, coverLink, fileSize, 
                description, additionalInfo, publishDate, author, viewsCount, commentsCount);
        this.quality = quality;
    }

    public FilmShortViewDTO(Film f) {
        super(f);
        this.quality = f.getQuality();
    }

    public FilmQuality getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return "FilmShortViewDTO{"
                + "id=" + id
                + ", header=" + header
                + ", note=" + note
                + ", name=" + name
                + ", originalName=" + originalName
                + ", genres=" + genres
                + ", quality=" + quality
                + ", releaseYear=" + releaseYear
                + ", coverLink=" + coverLink
                + ", fileSize=" + fileSize
                + ", description=" + description
                + ", additionalInfo=" + additionalInfo
                + ", publishDate=" + publishDate
                + ", author=" + author
                + ", viewsCount=" + viewsCount
                + ", commentsCount=" + commentsCount + '}';
    }
}
