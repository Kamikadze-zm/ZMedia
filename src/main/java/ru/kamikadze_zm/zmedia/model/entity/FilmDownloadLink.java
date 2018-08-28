package ru.kamikadze_zm.zmedia.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "film_download_links")
public class FilmDownloadLink extends DownloadLink<Film> implements Serializable {

    private static final long serialVersionUID = 1L;

    public FilmDownloadLink() {
    }

    public FilmDownloadLink(String description, String link) {
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
        if (!(object instanceof FilmDownloadLink)) {
            return false;
        }
        FilmDownloadLink other = (FilmDownloadLink) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FilmDownloadLink{" + super.toString() + '}';
    }
}
