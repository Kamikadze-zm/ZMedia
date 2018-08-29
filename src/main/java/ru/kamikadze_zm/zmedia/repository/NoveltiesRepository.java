package ru.kamikadze_zm.zmedia.repository;

import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.Publication;

public interface NoveltiesRepository {

    public List<Publication> getNovelties(int limit);
}
