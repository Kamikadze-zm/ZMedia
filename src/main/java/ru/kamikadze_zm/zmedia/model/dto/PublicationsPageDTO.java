package ru.kamikadze_zm.zmedia.model.dto;

import java.util.List;
import ru.kamikadze_zm.zmedia.model.Pagination;

public class PublicationsPageDTO {

    private final List<? extends PublicationShortViewDTO> publications;
    private final Pagination pagination;

    public PublicationsPageDTO(List<? extends PublicationShortViewDTO> publications, Pagination pagination) {
        this.publications = publications;
        this.pagination = pagination;
    }

    public List<? extends PublicationShortViewDTO> getPublications() {
        return publications;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
