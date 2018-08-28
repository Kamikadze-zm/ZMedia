package ru.kamikadze_zm.zmedia.model.dto;

import java.util.Date;
import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.model.entity.util.Genre;

public class PublicationShortViewDTO<G extends Enum<G> & Genre> {

    protected final Integer id;
    protected final String header;
    protected final String note;
    protected final String name;
    protected final String originalName;
    protected final List<G> genres;
    protected final Short releaseYear;
    protected final String coverLink;
    protected final Integer fileSize;
    protected final String description;
    protected final String additionalInfo;
    protected final long publishDate;
    protected final String author;
    protected final int viewsCount;
    protected final int commentsCount;

    public PublicationShortViewDTO(Integer id,
            String header,
            String note,
            String name,
            String originalName,
            List<G> genres,
            Short releaseYear,
            String coverLink,
            Integer fileSize,
            String description,
            String additionalInfo,
            Date publishDate,
            String author,
            int viewsCount,
            int commentsCount) {
        this.id = id;
        this.header = header;
        this.note = note;
        this.name = name;
        this.originalName = originalName;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.coverLink = coverLink;
        this.fileSize = fileSize;
        this.description = description;
        this.additionalInfo = additionalInfo;
        this.publishDate = publishDate.getTime();
        this.author = author;
        this.viewsCount = viewsCount;
        this.commentsCount = commentsCount;
    }

    public PublicationShortViewDTO(Publication<G, ?, ?> p) {
        this(
                p.getId(),
                p.getHeader(),
                p.getNote(),
                p.getName(),
                p.getOriginalName(),
                p.getGenres(),
                p.getReleaseYear(),
                p.getCoverLink(),
                p.getFileSize(),
                p.getDescription(),
                p.getAdditionalInfo(),
                p.getPublishDate(),
                p.getAuthor().getName(),
                p.getViewsCounter(),
                p.getComments().size());
    }

    public Integer getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public List<G> getGenres() {
        return genres;
    }

    public Short getReleaseYear() {
        return releaseYear;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public String getDescription() {
        return description;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public String getAuthor() {
        return author;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    @Override
    public String toString() {
        return "PublicationShortDTO{"
                + "id=" + id
                + ", header=" + header
                + ", note=" + note
                + ", name=" + name
                + ", originalName=" + originalName
                + ", genres=" + genres
                + ", releaseYear=" + releaseYear
                + ", coverLink=" + coverLink
                + ", fileSize=" + fileSize
                + ", description=" + description
                + ", additionalInfo=" + additionalInfo
                + ", publishDate=" + publishDate
                + ", author=" + author
                + ", viewsCount=" + viewsCount
                + ", commentsCount=" + commentsCount + '}';
    }
}
