package ru.kamikadze_zm.zmedia.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "tv_series_comments")
@AssociationOverride(name = "publication",
        joinColumns = @JoinColumn(name = "tv_series", referencedColumnName = "id"))
public class TvSeriesComment extends Comment<TvSeriesComment, TvSeries> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TvSeriesComment() {
    }

    public TvSeriesComment(String comment, Date commentDate, TvSeries publication, User author) {
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
        if (!(object instanceof TvSeriesComment)) {
            return false;
        }
        TvSeriesComment other = (TvSeriesComment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TvSeriesComment{" + super.toString() + '}';
    }

}
