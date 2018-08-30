package ru.kamikadze_zm.zmedia.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "games_download_links")
public class GameDownloadLink extends DownloadLink<Game> implements Serializable {

    private static final long serialVersionUID = 1L;

    public GameDownloadLink() {
    }

    public GameDownloadLink(String description, String link) {
        super(description, link);
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
        if (!(object instanceof GameDownloadLink)) {
            return false;
        }
        GameDownloadLink other = (GameDownloadLink) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameDownloadLink{" + super.toString() + '}';
    }
}
