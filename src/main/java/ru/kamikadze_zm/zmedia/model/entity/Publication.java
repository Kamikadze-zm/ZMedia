package ru.kamikadze_zm.zmedia.model.entity;

import ru.kamikadze_zm.zmedia.model.entity.util.Genre;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @param <G> enum жанров
 * @param <C> тип комментариев
 * @param <DL> тип ссылок
 */
@MappedSuperclass
public abstract class Publication<G extends Enum<G> & Genre, C extends Comment, DL extends DownloadLink> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "header")
    protected String header;
    @Size(max = 100)
    @Column(name = "note")
    protected String note;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "name")
    protected String name;
    @Size(max = 300)
    @Column(name = "original_name")
    protected String originalName;
    @Column(name = "release_year")
    @Min(1900)
    @Max(2100)
    protected Short releaseYear;
    @Size(max = 300)
    @Column(name = "genres")
    protected String genres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cover_link")
    protected String coverLink;
    @Column(name = "file_size")
    protected Integer fileSize;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50000)
    @Column(name = "description")
    protected String description;
    @Size(max = 50000)
    @Column(name = "additional_info")
    protected String additionalInfo;
    @Size(max = 50000)
    @Column(name = "details")
    protected String details;
    @Column(name = "publish_date")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date publishDate;
    @Column(name = "views_counter")
    protected int viewsCounter;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publication")
    protected Collection<C> comments;
    @JoinColumn(name = "author", referencedColumnName = "email")
    @ManyToOne
    protected User author;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "publication", referencedColumnName = "id", nullable = false)
    protected List<DL> downloadLinks;

    public Publication() {
    }

    public Publication(String header,
            String note,
            String name,
            String originalName,
            Short releaseYear,
            List<G> genres,
            String coverLink,
            Integer fileSize,
            String description,
            String additionalInfo,
            String details,
            Date publishDate) {
        this.header = header;
        this.note = note;
        this.name = name;
        this.originalName = originalName;
        this.releaseYear = releaseYear;
        this.genres = convertListGenresToString(genres);
        this.coverLink = coverLink;
        this.fileSize = fileSize;
        this.description = description;
        this.additionalInfo = additionalInfo;
        this.details = details;
        this.publishDate = publishDate;
    }

    public Publication(String header,
            String note,
            String name,
            String originalName,
            Short releaseYear,
            List<G> genres,
            String coverLink,
            Integer fileSize,
            String description,
            String additionalInfo,
            String details,
            Date publishDate,
            User author,
            List<DL> downloadLinks) {
        this(header, note, name, originalName, releaseYear, genres, coverLink, fileSize, description, additionalInfo, details, publishDate);
        this.author = author;
        this.downloadLinks = downloadLinks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Short getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Short releaseYear) {
        this.releaseYear = releaseYear;
    }

    public abstract List<G> getGenres();

    public String getStringGenres() {
        return this.genres;
    }

    public void setGenres(List<G> genres) {
        this.genres = convertListGenresToString(genres);
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getViewsCounter() {
        return viewsCounter;
    }

    public void setViewsCounter(int viewsCounter) {
        this.viewsCounter = viewsCounter;
    }

    public Collection<C> getComments() {
        return comments;
    }

    public void setComments(Collection<C> comments) {
        this.comments = comments;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<DL> getDownloadLinks() {
        return downloadLinks;
    }

    public void setDownloadLinks(List<DL> downloadLinks) {
        this.downloadLinks = downloadLinks;
    }

    @Override
    public String toString() {
        return "Publication{" + "id=" + id
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

    protected static <G extends Enum<G> & Genre> String convertListGenresToString(List<G> genres) {
        if (genres == null || genres.isEmpty()) {
            return null;
        }
        Collections.sort(genres, Genre.getComparator());
        List<String> genresList = new ArrayList<>();
        genres.stream().forEach(g -> genresList.add(g.getGenre()));
        return String.join(", ", genresList);
    }

    protected static <G extends Enum<G> & Genre> List<G> convertStringGenresToList(Class<G> enumClass,
            String genres) {
        if (genres != null && !genres.isEmpty()) {
            String[] genresArr = genres.split(", ");
            List<G> genresList = new ArrayList<>();
            EnumSet<G> enumSet = EnumSet.allOf(enumClass);
            for (String s : genresArr) {
                Optional<G> og = enumSet.stream().filter(e -> e.getGenre().equalsIgnoreCase(s)).findFirst();
                if (og.isPresent()) {
                    genresList.add(og.get());
                }
            }
            return genresList;
        } else {
            return null;
        }
    }
}
