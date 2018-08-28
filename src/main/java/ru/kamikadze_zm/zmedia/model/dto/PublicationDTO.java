package ru.kamikadze_zm.zmedia.model.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import ru.kamikadze_zm.zmedia.util.StringUtil;

/**
 * @param <G> enum жанров.
 */
public class PublicationDTO<G extends Enum<G>> {

    @NotBlank(message = "Заголовок не должен быть пустым")
    @Size(max = 500, message = "Заголовок должен содержать не более {max} символов")
    protected String header;
    @Size(max = 100, message = "Заметка должна содержать не более {max} символов")
    protected String note;
    @NotBlank(message = "Название не должно быть пустым")
    @Size(max = 300, message = "Название должно содержать не более {max} символов")
    protected String name;
    @Size(max = 300, message = "Оригинальное название должно содержать не более {max} символов")
    protected String originalName;
    @NotEmpty(message = "Выберите жанр")
    protected List<G> genres;
    @Min(value = 1900, message = "Год должен быть больше или равен {value}")
    @Max(value = 2100, message = "Год должен быть меньше или равен {value}")
    protected Short releaseYear;
    @NotNull(message = "Выберите обложку")
    protected String coverLink;
    @NotNull(message = "Укажите размер файла")
    protected Integer fileSize;
    @Size(max = 50000, message = "Описание должно содержать не более {max} символов")
    protected String description;
    @Size(max = 50000, message = "Дополнительная информация должна содержать не более {max} символов")
    protected String additionalInfo;
    @Size(max = 50000, message = "Подробности должны содержать не более {max} символов")
    protected String details;
    @NotEmpty(message = "Должна быть указана хотя бы одна ссылка")
    @Valid
    protected List<DownloadLinkDTO> downloadLinks;

    public PublicationDTO() {
    }

    public PublicationDTO(
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
            String details,
            List<DownloadLinkDTO> downloadLinks) {
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
        this.details = details;
        this.downloadLinks = downloadLinks;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNote() {
        if (StringUtil.isEmptyString(note)) {
            return null;
        }
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
        if (StringUtil.isEmptyString(originalName)) {
            return null;
        }
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public List<G> getGenres() {
        return genres;
    }

    public void setGenres(List<G> genres) {
        this.genres = genres;
    }

    public Short getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Short releaseYear) {
        this.releaseYear = releaseYear;
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
        if (StringUtil.isEmptyString(description)) {
            return null;
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionalInfo() {
        if (StringUtil.isEmptyString(additionalInfo)) {
            return null;
        }
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getDetails() {
        if (StringUtil.isEmptyString(details)) {
            return null;
        }
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<DownloadLinkDTO> getDownloadLinks() {
        return downloadLinks;
    }

    public void setDownloadLinks(List<DownloadLinkDTO> downloadLinks) {
        this.downloadLinks = downloadLinks;
    }

    @Override
    public String toString() {
        return "PublicationDTO{"
                + "header=" + header
                + ", note=" + note
                + ", name=" + name
                + ", originalName=" + originalName
                + ", genres=" + genres
                + ", releaseYear=" + releaseYear
                + ", coverLink=" + coverLink
                + ", fileSize=" + fileSize
                + ", description=" + description
                + ", additionalInfo=" + additionalInfo
                + ", details=" + details
                + ", downloadLinks=" + downloadLinks + '}';
    }
}
