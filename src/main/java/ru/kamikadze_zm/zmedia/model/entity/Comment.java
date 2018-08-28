package ru.kamikadze_zm.zmedia.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;
import ru.kamikadze_zm.zmedia.model.entity.util.PostgreSqlEnumType;

/**
 * @param <C> тип комментария
 * @param <P> тип публикации
 */
@MappedSuperclass
public abstract class Comment<C extends Comment, P extends Publication> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10000)
    @Column(name = "comment")
    private String comment;
    @Column(name = "status")
    @Type(type = PostgreSqlEnumType.POSTGRESQL_ENUM_TYPE)
    @Enumerated(EnumType.STRING)
    private CommentStatus status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "comment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate;
    @Column(name = "changed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changedDate;
    @OneToMany(mappedBy = "parent")
    private Collection<C> childs;
    @JoinColumn(name = "parent", referencedColumnName = "id")
    @ManyToOne
    private C parent;
    @ManyToOne(optional = false)
    private P publication;
    @JoinColumn(name = "author", referencedColumnName = "email")
    @ManyToOne
    private User author;

    public Comment() {
    }

    ;
    
    public Comment(String comment, Date commentDate, P publication, User author) {
        this.comment = comment;
        this.commentDate = commentDate;
        this.publication = publication;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Date getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(Date changedDate) {
        this.changedDate = changedDate;
    }

    public Collection<C> getChilds() {
        return childs;
    }

    public void setChilds(Collection<C> childs) {
        this.childs = childs;
    }

    public C getParent() {
        return parent;
    }

    public void setParent(C parent) {
        this.parent = parent;
    }

    public P getPublication() {
        return publication;
    }

    public void setPublication(P publication) {
        this.publication = publication;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id
                + ", comment=" + comment
                + ", status=" + status
                + ", commentDate=" + commentDate
                + ", changedDate=" + changedDate
                + ", parent=" + parent
                + ", author=" + author + '}';
    }

    public static enum CommentStatus {
        CBM("Комментарий изменен модератором"),
        CBU("Комментарий изменен пользователем"),
        DBM("Комментарий удален модератором"),
        DBU("Комментарий удален пользователем");

        private final String message;

        private CommentStatus(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
