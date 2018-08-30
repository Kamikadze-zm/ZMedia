package ru.kamikadze_zm.zmedia.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "games_comments")
@AssociationOverride(name = "publication",
        joinColumns = @JoinColumn(name = "game", referencedColumnName = "id"))
public class GameComment extends Comment<GameComment, Game> implements Serializable {

    private static final long serialVersionUID = 1L;

    public GameComment() {
    }

    public GameComment(String comment, Date commentDate, Game publication, User author) {
        super(comment, commentDate, publication, author);
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
        if (!(object instanceof GameComment)) {
            return false;
        }
        GameComment other = (GameComment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameComment{" + super.toString() + '}';
    }
}
