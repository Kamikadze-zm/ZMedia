package ru.kamikadze_zm.zmedia.model.dto;

import java.util.regex.Pattern;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class CommentDTO {

    private static final Pattern QUOTE_PATTERN = Pattern.compile("\\[quote\\]", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSE_QUOTE_PATTERN = Pattern.compile("\\[\\/quote\\]", Pattern.CASE_INSENSITIVE);
    private static final Pattern USER_PATTERN = Pattern.compile("\\[user\\]", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSE_USER_PATTERN = Pattern.compile("\\[\\/user\\]", Pattern.CASE_INSENSITIVE);
    private static final String QUOTE_TAG = "<cite>";
    private static final String CLOSE_QUOTE_TAG = "</cite><br/>";
    private static final String USER_TAG = "<span class=\"user-name\">";
    private static final String CLOSE_USER_TAG = ", </span>";

    @NotBlank(message = "Введите текст комментария")
    @Size(max = 10000, message = "Комментарий должен содержать не более {max} символов")
    private String comment;
    private Integer parentId;

    public CommentDTO() {
    }

    public CommentDTO(String comment, Integer parentId) {
        this.comment = getSafeComment(comment);
        this.parentId = parentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = getSafeComment(comment);
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    private String getSafeComment(String comment) {
        String safeHtml = Jsoup.clean(comment, Whitelist.none());
        safeHtml = QUOTE_PATTERN.matcher(safeHtml).replaceAll(QUOTE_TAG);
        safeHtml = CLOSE_QUOTE_PATTERN.matcher(safeHtml).replaceAll(CLOSE_QUOTE_TAG);
        safeHtml = USER_PATTERN.matcher(safeHtml).replaceAll(USER_TAG);
        safeHtml = CLOSE_USER_PATTERN.matcher(safeHtml).replaceAll(CLOSE_USER_TAG);
        return safeHtml;
    }

    @Override
    public String toString() {
        return "CommentDTO{" + "comment=" + comment + ", parentId=" + parentId + '}';
    }
}
