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
        if (publication instanceof Film) {
            this.publication = new FilmShortViewDTO((Film) publication);
        } else if (publication instanceof TvSeries) {
            this.publication = new TvSeriesShortViewDTO((TvSeries) publication);
        } else if (publication instanceof Game) {
            this.publication = new GameShortViewDTO((Game) publication);
        } else {
            this.publication = new PublicationShortViewDTO(publication);
        }
        this.type = PublicationType.identifyType(publication);
    }

    public PublicationShortViewDTO getPublication() {
        return publication;
    }

    public PublicationType getType() {
        return type;
    }
}
