package ru.kamikadze_zm.zmedia.model.dto;

import java.util.Date;
import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmGenre;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmQuality;
import ru.kamikadze_zm.zmedia.model.entity.TvSeries;

public class TvSeriesShortViewDTO extends PublicationShortViewDTO<FilmGenre> {

    private final Short seasonNumber;
    private final FilmQuality quality;

    public TvSeriesShortViewDTO(
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
            FilmQuality quality,
            Short seasonNumber) {
        super(id, header, note, name, originalName, genres, releaseYear, coverLink, fileSize,
                description, additionalInfo, publishDate, author, viewsCount, commentsCount);
        this.seasonNumber = seasonNumber;
        this.quality = quality;
    }

    public TvSeriesShortViewDTO(TvSeries s) {
        super(s);
        this.seasonNumber = s.getSeasonNumber();
        this.quality = s.getQuality();
    }

    public Short getSeasonNumber() {
        return seasonNumber;
    }

    public FilmQuality getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return "TvSeriesShortViewDTO{"
                + "id=" + id
                + ", header=" + header
                + ", note=" + note
                + ", name=" + name
                + ", originalName=" + originalName
                + ", seasonNumber=" + seasonNumber
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
