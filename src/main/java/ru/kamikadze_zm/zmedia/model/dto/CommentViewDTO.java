package ru.kamikadze_zm.zmedia.model.dto;

import java.util.Date;
import ru.kamikadze_zm.zmedia.model.entity.Comment.CommentStatus;

public class CommentViewDTO {

    private static final String COMMENT_IS_DELETED_MESSAGE = "Комментарий удалён";

    private final int id;
    private final int level;
    private final String comment;
    private final CommentStatus status;
    private final long commentDate;
    private final Long changedDate;
    private final String authorName;
    private final String authorAvatar;

    public CommentViewDTO(int id, int level, String comment, CommentStatus status, Date commentDate, Date changedDate, String authorName, String authorAvatar) {
        this.id = id;
        this.level = level;
        if (CommentStatus.DBU != status && CommentStatus.DBM != status) {
            this.comment = comment;
        } else {
            this.comment = COMMENT_IS_DELETED_MESSAGE;
        }
        this.status = status;
        this.commentDate = commentDate.getTime();
        if (changedDate != null) {
            this.changedDate = changedDate.getTime();
        } else {
            this.changedDate = null;
        }
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public String getComment() {
        return comment;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public long getCommentDate() {
        return commentDate;
    }

    public Long getChangedDate() {
        return changedDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    @Override
    public String toString() {
        return "CommentViewDTO{"
                + "id=" + id
                + ", level=" + level
                + ", comment=" + comment
                + ", status=" + status
                + ", commentDate=" + commentDate
                + ", changedDate=" + changedDate
                + ", authorName=" + authorName
                + ", authorAvatar=" + authorAvatar + '}';
    }
}
