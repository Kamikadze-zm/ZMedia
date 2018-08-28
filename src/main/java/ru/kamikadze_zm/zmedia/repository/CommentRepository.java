package ru.kamikadze_zm.zmedia.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import ru.kamikadze_zm.zmedia.model.entity.Comment;

@NoRepositoryBean
public interface CommentRepository<E extends Comment> extends Repository<E, Integer> {

    public List<E> findByPublicationId(Integer id);

    public long countByPublicationId(Integer id);
}
