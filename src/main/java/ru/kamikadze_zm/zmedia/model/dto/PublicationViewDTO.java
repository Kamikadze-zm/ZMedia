package ru.kamikadze_zm.zmedia.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.DownloadLink;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.model.entity.util.Genre;

public class PublicationViewDTO<G extends Enum<G> & Genre> extends PublicationShortViewDTO<G> {

    protected final String details;
    protected final List<DownloadLinkDTO> downloadLinks;

    public PublicationViewDTO(Integer id,
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
            List<? extends DownloadLink> downloadLinks,
            Date publishDate,
            String author,
            int viewsCount,
            int commentsCount) {
        super(id, header, note, name, originalName, genres, releaseYear, coverLink, 
                fileSize, description, additionalInfo, publishDate, author, viewsCount, commentsCount);
        this.details = details;
        this.downloadLinks = new ArrayList<>();
        downloadLinks.forEach(l -> this.downloadLinks.add(new DownloadLinkDTO(l.getDescription(), l.getLink())));
    }

    public PublicationViewDTO(Publication<G, ?, ? extends DownloadLink> p) {
        super(p);
        this.details = p.getDetails();
        this.downloadLinks = new ArrayList<>();
        p.getDownloadLinks().forEach(l -> this.downloadLinks.add(new DownloadLinkDTO(l.getDescription(), l.getLink())));
    }

    public String getDetails() {
        return details;
    }

    public List<DownloadLinkDTO> getDownloadLinks() {
        return downloadLinks;
    }

    @Override
    public String toString() {
        return "PublicationViewDTO{"
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
                + ", details=" + details
                + ", downloadLinks=" + downloadLinks
                + ", publishDate=" + publishDate
                + ", author=" + author
                + ", viewsCount=" + viewsCount
                + ", commentsCount=" + commentsCount + '}';
    }
}
