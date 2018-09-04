package ru.kamikadze_zm.zmedia.model.dto;

import ru.kamikadze_zm.zmedia.model.entity.Film;
import ru.kamikadze_zm.zmedia.model.entity.Game;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.model.entity.TvSeries;
import ru.kamikadze_zm.zmedia.model.entity.util.PublicationType;

public class SearchResult {

    private final PublicationShortViewDTO publication;
    private final PublicationType type;

    @SuppressWarnings("unchecked")
    public SearchResult(Publication publication) {
        this.type = PublicationType.identifyType(publication);
        switch (this.type) {
            case FILM:
                this.publication = new FilmShortViewDTO((Film) publication);
                break;
            case TV_SERIES:
                this.publication = new TvSeriesShortViewDTO((TvSeries) publication);
                break;
            case GAME:
                this.publication = new GameShortViewDTO((Game) publication);
                break;
            default:
                this.publication = new PublicationShortViewDTO(publication);
        }
    }

    public PublicationShortViewDTO getPublication() {
        return publication;
    }

    public PublicationType getType() {
        return type;
    }
}
