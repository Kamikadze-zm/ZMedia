package ru.kamikadze_zm.zmedia.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kamikadze_zm.zmedia.model.dto.DownloadLinkDTO;
import ru.kamikadze_zm.zmedia.model.dto.GameDTO;
import ru.kamikadze_zm.zmedia.model.dto.GameShortViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.GameViewDTO;
import ru.kamikadze_zm.zmedia.model.entity.DownloadLink;
import ru.kamikadze_zm.zmedia.model.entity.Game;
import ru.kamikadze_zm.zmedia.model.entity.GameDownloadLink;
import ru.kamikadze_zm.zmedia.repository.GameRepository;
import ru.kamikadze_zm.zmedia.service.GameService;

@Service
public class GameServiceImpl extends PublicationServiceImpl<Game, GameDTO, GameShortViewDTO, GameViewDTO> implements GameService {

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        super(gameRepository);
    }

    @Override
    protected GameShortViewDTO mapPublicationToShortView(Game publication) {
        return new GameShortViewDTO(publication);
    }

    @Override
    protected GameViewDTO mapPublicationToFullView(Game publication) {
        return new GameViewDTO(publication);
    }

    @Override
    protected Game mapDtoToEntity(GameDTO dto) {
        Game g = new Game();
        mapDtoToEntity(dto, g);
        return g;
    }

    @Override
    protected void mapDtoToEntity(GameDTO dto, Game entity) {
        mapDtoToPublication(dto, entity);
    }

    @Override
    protected void mapDownloadLinks(List<? super DownloadLink> entityLinks, List<DownloadLinkDTO> dtoLinks) {
        dtoLinks.forEach(l -> entityLinks.add(new GameDownloadLink(l.getDescription(), l.getLink())));
    }
}
