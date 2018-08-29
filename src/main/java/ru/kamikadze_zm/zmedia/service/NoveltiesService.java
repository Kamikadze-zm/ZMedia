package ru.kamikadze_zm.zmedia.service;

import java.util.List;
import ru.kamikadze_zm.zmedia.model.dto.NoveltyDTO;

public interface NoveltiesService {

    public List<NoveltyDTO> getNovelties();
}
