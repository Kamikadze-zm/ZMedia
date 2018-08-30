package ru.kamikadze_zm.zmedia.service;

import ru.kamikadze_zm.zmedia.model.dto.GameDTO;
import ru.kamikadze_zm.zmedia.model.dto.GameShortViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.GameViewDTO;
import ru.kamikadze_zm.zmedia.model.entity.Game;

public interface GameService extends PublicationService<Game, GameDTO, GameShortViewDTO, GameViewDTO> {

}
