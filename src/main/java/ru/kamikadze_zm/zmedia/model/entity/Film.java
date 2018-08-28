package ru.kamikadze_zm.zmedia.model.entity;

import ru.kamikadze_zm.zmedia.model.entity.util.Genre;
import ru.kamikadze_zm.zmedia.model.entity.util.Quality;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
import ru.kamikadze_zm.zmedia.model.entity.util.FilmGenreSerializer;
import ru.kamikadze_zm.zmedia.model.entity.util.FilmQualitySerializer;
import ru.kamikadze_zm.zmedia.model.entity.util.PostgreSqlEnumType;

@Entity
@Table(name = "films")
@AssociationOverride(name = "downloadLinks",
        joinColumns = @JoinColumn(name = "film", referencedColumnName = "id", nullable = false))
public class Film extends Publication<FilmGenre, FilmComment, FilmDownloadLink> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quality")
    @Type(type = PostgreSqlEnumType.POSTGRESQL_ENUM_TYPE)
    @Enumerated(EnumType.STRING)
    private FilmQuality quality;

    public Film() {
    }

    public Film(String header,
            String note,
            String name,
            String originalName,
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
        this.quality = quality;
    }

    public Film(String header,
            String note,
            String name,
            String originalName,
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
            List<FilmDownloadLink> downloadLinks) {
        super(header, note, name, originalName, releaseYear, genres, coverLink, fileSize, description,
                additionalInfo, details, publishDate, author, downloadLinks);
        this.quality = quality;
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
        if (!(object instanceof Film)) {
            return false;
        }
        Film other = (Film) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Film{" + "id=" + id
                + ", header=" + header
                + ", note=" + note
                + ", name=" + name
                + ", originalName=" + originalName
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

    @JsonSerialize(using = FilmQualitySerializer.class)
    public static enum FilmQuality implements Quality {
        BDREMUX("BDRemux"),
        BDRIP("BDRip"), BDRIP720P("BDRip 720p"), BDRIP1080P("BDRip 1080p"),
        BLURAY("BLU-RAY"),
        DVD("DVD"), DVDRIP("DVDRip"),
        HDRIP("HDRip"),
        HDTV720P("HDTV 720p"), HDTV1080I("HDTV 1080i"), HDTV1080P("HDTV 1080p"),
        HDTVRIP("HDTVRip"), HDTVRIP720P("HDTVRip 720p"), HDTVRIP1080P("HDTVRip 1080p"),
        SATRIP("SATRip"), TVRIP("TVRip"),
        WEBDL720P("WEB-DL 720p"), WEBDL1080P("WEB-DL 1080p"),
        WEBDLRIP("WEB-DLRip"), WEBDLRIP720P("WEB-DLRip 720p"), WEBDLRIP1080P("WEB-DLRip 1080p"),
        WEBRIP("WEBRip"), WEBRIP720P("WEBRip 720p"), WEBRIP1080P("WEBRip 1080p"),
        TS("TS"), CAMRIP("CAMRip"), OTHER("Другое");

        private final String quality;

        private FilmQuality(String quality) {
            this.quality = quality;
        }

        @Override
        public String getQuality() {
            return quality;
        }
    }

    @JsonSerialize(using = FilmGenreSerializer.class)
    public static enum FilmGenre implements Genre {
        ANIME("Аниме"),
        BIOGRAPHY("Биография"),
        ACTION("Боевик"),
        WESTERN("Вестерн"),
        WAR("Военный"),
        DETECTIVE("Детектив"),
        DOCUMENTARY("Документальный"),
        DRAMA("Драма"),
        GAMESHOW("Игра"),
        HISTORY("История"),
        COMEDY("Комедия"),
        CRIME("Криминал"),
        ROMANCE("Мелодрама"),
        MYSTERY("Мистика"),
        MUSIC("Музыка"),
        ANIMATION("Мультфильм"),
        MUSICAL("Мюзикл"),
        NEWS("Новости"),
        FILMNOIR("Нуар"),
        ADVENTURE("Приключения"),
        REALITYTV("Реальное ТВ"),
        FAMILY("Семейный"),
        SITCOM("Ситком"),
        SPORT("Спорт"),
        TALKSHOW("Ток-шоу"),
        THRILLER("Триллер"),
        HORROR("Ужасы"),
        SCIFI("Фантастика"),
        FANTASY("Фэнтези"),
        OTHER("Другой");

        private final String genre;

        private FilmGenre(String genre) {
            this.genre = genre;
        }

        @Override
        public String getGenre() {
            return genre;
        }
    }
}
