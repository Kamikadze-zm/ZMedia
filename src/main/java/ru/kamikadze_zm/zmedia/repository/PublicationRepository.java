package ru.kamikadze_zm.zmedia.repository;

import org.springframework.data.repository.NoRepositoryBean;
import ru.kamikadze_zm.zmedia.model.entity.Publication;

@NoRepositoryBean
public interface PublicationRepository<E extends Publication> extends Repository<E, Integer> {

}
