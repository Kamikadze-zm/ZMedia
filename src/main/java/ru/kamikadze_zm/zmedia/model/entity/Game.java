package ru.kamikadze_zm.zmedia.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import ru.kamikadze_zm.zmedia.model.entity.Game.GameGenre;
import ru.kamikadze_zm.zmedia.model.entity.util.GameGenreSerializer;
import ru.kamikadze_zm.zmedia.model.entity.util.Genre;

@Entity
@Table(name = "games")
@AssociationOverride(name = "downloadLinks",
        joinColumns = @JoinColumn(name = "game", referencedColumnName = "id", nullable = false))
public class Game extends Publication<GameGenre, GameComment, GameDownloadLink> implements Serializable {

    private static final long serialVersionUID = 1L;

    public Game() {
    }

    public Game(String header,
            String note,
            String name,
            String originalName,
            Short releaseYear,
            List<GameGenre> genres,
            String coverLink,
            Integer fileSize,
            String description,
            String additionalInfo,
            String details,
            Date publishDate) {
        super(header, note, name, originalName, releaseYear, genres, coverLink, fileSize, description, additionalInfo, details, publishDate);
    }

    public Game(String header,
            String note,
            String name,
            String originalName,
            Short releaseYear,
            List<GameGenre> genres,
            String coverLink,
            Integer fileSize,
            String description,
            String additionalInfo,
            String details,
            Date publishDate,
            User author,
            List<GameDownloadLink> downloadLinks) {
        super(header, note, name, originalName, releaseYear, genres, coverLink, fileSize, description,
                additionalInfo, details, publishDate, author, downloadLinks);
    }

    @Override
    public List<GameGenre> getGenres() {
        return convertStringGenresToList(GameGenre.class, genres);
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
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Game{" + "id=" + id
                + ", header=" + header
                + ", note=" + note
                + ", name=" + name
                + ", originalName=" + originalName
                + ", releaseYear=" + releaseYear
                + ", genres=" + genres
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

    @JsonSerialize(using = GameGenreSerializer.class)
    public static enum GameGenre implements Genre {
        ARCADE("Аркада"),
        PUZZLE("Головоломка"),
        RACING("Гонки"),
        QUEST("Квест"),
        MMO("ММО"),
        MOBA("МОБА"),
        PLATFORM("Платформер"),
        ADVENTURE("Приключения"),
        RPG("Ролевая игра"),
        SIMULATION("Симулятор"),
        SPORTS("Спорт"),
        STRATEGY("Стратегия"),
        FIGHTING("Файтинг"),
        ACTION("Экшен"),
        OTHER("Другой");

        private final String genre;

        private GameGenre(String genre) {
            this.genre = genre;
        }

        @Override
        public String getGenre() {
            return genre;
        }
    }
}
