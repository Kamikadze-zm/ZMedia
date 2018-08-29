package ru.kamikadze_zm.zmedia.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.AssociationOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmGenre;
import ru.kamikadze_zm.zmedia.model.entity.Film.FilmQuality;
import ru.kamikadze_zm.zmedia.model.entity.util.PostgreSqlEnumType;

@Entity
@Table(name = "tv_series")
@AssociationOverride(name = "downloadLinks",
        joinColumns = @JoinColumn(name = "tv_series", referencedColumnName = "id", nullable = false))
public class TvSeries extends Publication<FilmGenre, TvSeriesComment, TvSeriesDownloadLink> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "season_number")
    private Short seasonNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quality")
    @Type(type = PostgreSqlEnumType.POSTGRESQL_ENUM_TYPE)
    @Enumerated(EnumType.STRING)
    private FilmQuality quality;

    public TvSeries() {
    }

    public TvSeries(String header,
            String note,
            String name,
            String originalName,
            Short seasonNumber,
            Short releaseYear,
            List<FilmGenre> genres,
            FilmQuality quality,
            String coverLink,
            Integer fileSize,
            String description,
            String additionalInfo,
            String details,
            Date publishDate) {
        super(header, note, name, originalName, releaseYear, genres, coverLink, fileSize, description, additionalInfo, details, publishDate);
        this.seasonNumber = seasonNumber;
        this.quality = quality;
    }

    public TvSeries(String header,
            String note,
            String name,
            String originalName,
            Short seasonNumber,
            Short releaseYear,
            List<FilmGenre> genres,
            FilmQuality quality,
            String coverLink,
            Integer fileSize,
            String description,
            String additionalInfo,
            String details,
            Date publishDate,
            User author,
            List<TvSeriesDownloadLink> downloadLinks) {
        super(header, note, name, originalName, releaseYear, genres, coverLink, fileSize, description,
                additionalInfo, details, publishDate, author, downloadLinks);
        this.seasonNumber = seasonNumber;
        this.quality = quality;
    }

    public Short getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Short seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    @Override
    public List<FilmGenre> getGenres() {
        return convertStringGenresToList(FilmGenre.class, genres);
    }

    public FilmQuality getQuality() {
        return quality;
    }

    public void setQuality(FilmQuality quality) {
        this.quality = quality;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TvSeries)) {
            return false;
        }
        TvSeries other = (TvSeries) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TvSeries{" + "id=" + id
                + ", header=" + header
                + ", note=" + note
                + ", name=" + name
                + ", originalName=" + originalName
                + ", seasonNumber=" + seasonNumber
                + ", releaseYear=" + releaseYear
                + ", genres=" + genres
                + ", quality=" + quality
                + ", coverLink=" + coverLink
                + ", fileSize=" + fileSize
                + ", description=" + description
                + ", additionalInfo=" + additionalInfo
                + ", details=" + details
                + ", publishDate=" + publishDate
                + ", viewsCounter=" + viewsCounter
                + ", comments=" + comments
                + ", author=" + author
                + ", downloadLinks=" + downloadLinks + '}';
    }
}
