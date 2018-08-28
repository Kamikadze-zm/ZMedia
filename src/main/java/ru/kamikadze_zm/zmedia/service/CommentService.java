package ru.kamikadze_zm.zmedia.service;

import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.kamikadze_zm.zmedia.model.dto.CommentDTO;
import ru.kamikadze_zm.zmedia.model.dto.CommentViewDTO;
import ru.kamikadze_zm.zmedia.model.entity.Comment;
import ru.kamikadze_zm.zmedia.model.entity.Publication;

public interface CommentService<P extends Publication, E extends Comment<E, P>> {

    /**
     * Возвращает дерево комментариев для соответствующей публикации.
     *
     * @param publicationId id публикации.
     * @return дерево комментариев.
     */
    public List<CommentViewDTO> getCommentsTreeByPublicationId(Integer publicationId);

    public void add(Integer publicationId, CommentDTO dto);

    public void update(Integer id, CommentDTO dto) throws AccessDeniedException;

    public void markDeleted(Integer id) throws AccessDeniedException;

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(Integer id);
}
