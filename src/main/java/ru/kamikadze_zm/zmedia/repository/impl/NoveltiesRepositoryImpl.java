package ru.kamikadze_zm.zmedia.repository.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.kamikadze_zm.zmedia.model.entity.Film;
import ru.kamikadze_zm.zmedia.model.entity.Game;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.model.entity.TvSeries;
import ru.kamikadze_zm.zmedia.repository.NoveltiesRepository;

@Repository
public class NoveltiesRepositoryImpl implements NoveltiesRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Publication> getNovelties(int limit) {
        List<Publication> publications = new ArrayList<>();
        publications.addAll(getPublications(Film.class, limit));
        publications.addAll(getPublications(TvSeries.class, limit));
        publications.addAll(getPublications(Game.class, limit));
        publications.sort((Publication p1, Publication p2) -> p2.getPublishDate().compareTo(p1.getPublishDate()));
        if (publications.size() > limit) {
            return publications.subList(0, limit);
        } else {
            return publications;
        }
    }

    private <T extends Publication> List<T> getPublications(Class<T> type, int limit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(type);
        Root<T> root = criteria.from(type);
        criteria.select(root).orderBy(cb.desc(root.get("publishDate")));
        return em.createQuery(criteria).setMaxResults(limit).getResultList();
    }
}
