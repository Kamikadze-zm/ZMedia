package ru.kamikadze_zm.zmedia.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import ru.kamikadze_zm.zmedia.model.entity.Publication;

@NoRepositoryBean
public interface PublicationRepository<E extends Publication> extends Repository<E, Integer> {

    /**
     *
     * @param name название в нижнем регистре
     * @return список найденых публикаций
     */
    @Query("select p from #{#entityName} p where lower(p.name) like %?1% or lower(p.originalName) like %?1%")
    public List<E> searchByName(String name);
}
