package ru.kamikadze_zm.zmedia.model.dto;

import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.model.entity.util.PublicationType;

public class NoveltyDTO {

    private final Integer id;
    private final String header;
    private final String note;
    private final String coverLink;
    private final String description;
    private final String type;

    public NoveltyDTO(Integer id,
            String header,
            String note,
            String coverLink,
            String description,
            PublicationType type) {
        this.id = id;
        this.header = header;
        this.note = note;
        this.coverLink = coverLink;
        this.description = description;
        this.type = type.toString();
    }

    public NoveltyDTO(Publication p) {
        this.id = p.getId();
        this.header = p.getHeader();
        this.note = p.getNote();
        this.coverLink = p.getCoverLink();
        this.description = p.getDescription();
        this.type = PublicationType.identifyType(p).toString();
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

    public String getCoverLink() {
        return coverLink;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "NoveltyDTO{" + "id=" + id
                + ", header=" + header
                + ", note=" + note
                + ", coverLink=" + coverLink
                + ", description=" + description
                + ", type=" + type + '}';
    }
}
