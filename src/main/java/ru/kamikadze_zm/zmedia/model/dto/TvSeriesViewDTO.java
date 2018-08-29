package ru.kamikadze_zm.zmedia.model.dto;

import java.util.Date;
import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmGenre;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmQuality;
import ru.kamikadze_zm.zmedia.model.entity.TvSeries;
import ru.kamikadze_zm.zmedia.model.entity.TvSeriesDownloadLink;

public class TvSeriesViewDTO extends PublicationViewDTO<FilmGenre> {

    private final Short seasonNumber;
    private final FilmQuality quality;

    public TvSeriesViewDTO(
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
            String details,
            List<TvSeriesDownloadLink> downloadLinks,
            Date publishDate,
            String author,
            int viewsCount,
            int commentsCount,
            FilmQuality quality,
            Short seasonNumber) {
        super(id, header, note, name, originalName, genres, releaseYear, coverLink, fileSize, description, additionalInfo, details,
                downloadLinks, publishDate, author, viewsCount, commentsCount);
        this.seasonNumber = seasonNumber;
        this.quality = quality;
    }

    public TvSeriesViewDTO(TvSeries s) {
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
        return "TvSeriesViewDTO{"
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
                + ", details=" + details
                + ", downloadLinks=" + downloadLinks
                + ", publishDate=" + publishDate
                + ", author=" + author
                + ", viewsCount=" + viewsCount
                + ", commentsCount=" + commentsCount + '}';
    }
}
