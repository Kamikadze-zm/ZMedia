package ru.kamikadze_zm.zmedia.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kamikadze_zm.zmedia.model.dto.CommentDTO;
import ru.kamikadze_zm.zmedia.model.dto.CommentViewDTO;
import ru.kamikadze_zm.zmedia.model.entity.Comment;
import ru.kamikadze_zm.zmedia.model.entity.Comment.CommentStatus;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.repository.CommentRepository;
import ru.kamikadze_zm.zmedia.service.CommentService;
import ru.kamikadze_zm.zmedia.service.PublicationService;
import ru.kamikadze_zm.zmedia.util.AuthenticationUtil;
import ru.kamikadze_zm.zmedia.util.Constants;

@Service
public abstract class CommentServiceImpl<P extends Publication, E extends Comment<E, P>> implements CommentService<P, E> {

    private final CommentRepository<E> commentRepository;
    private final PublicationService<P, ?, ?, ?> publicationService;

    public CommentServiceImpl(CommentRepository<E> commentRepository, PublicationService<P, ?, ?, ?> publicationService) {
        this.commentRepository = commentRepository;
        this.publicationService = publicationService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentViewDTO> getCommentsTreeByPublicationId(Integer publicationId) {
        return buildTree(commentRepository.findByPublicationIdOrderByCommentDate(publicationId));
    }

    @Override
    @Transactional
    public void add(Integer publicationId, CommentDTO dto) {
        E e = mapDtoToEntity(dto);
        e.setPublication(publicationService.load(publicationId));
        commentRepository.save(e);
    }

    @Override
    @Transactional
    public void update(Integer id, CommentDTO dto) throws AccessDeniedException {
        E e = commentRepository.findById(id).get();
        if (!AuthenticationUtil.compareUserEmail(e.getAuthor().getEmail()) && !AuthenticationUtil.isAdmin()) {
            throw new AccessDeniedException(Constants.ACCESS_DENIED_MESSAGE);
        }
        mapDtoToEntity(dto, e);
        commentRepository.save(e);
    }

    @Override
    @Transactional
    public void markDeleted(Integer id) throws AccessDeniedException {
        E e = commentRepository.findById(id).get();
        if (!AuthenticationUtil.compareUserEmail(e.getAuthor().getEmail()) && !AuthenticationUtil.isAdmin()) {
            throw new AccessDeniedException(Constants.ACCESS_DENIED_MESSAGE);
        }
        e.setChangedDate(new Date());
        if (AuthenticationUtil.compareUserEmail(e.getAuthor().getEmail())) {
            e.setStatus(CommentStatus.DBU);
        } else {
            e.setStatus(CommentStatus.DBM);
        }
        commentRepository.save(e);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

    protected void mapDtoToComment(CommentDTO dto, E comment) {
        comment.setComment(dto.getComment());
        if (dto.getParentId() != null) {
            comment.setParent(commentRepository.getOne(dto.getParentId()));
        }
        if (comment.getAuthor() == null) {
            comment.setAuthor(AuthenticationUtil.getAuthenticatedUser());
            comment.setCommentDate(new Date());
        } else {
            comment.setChangedDate(new Date());
            if (AuthenticationUtil.compareUserEmail(comment.getAuthor().getEmail())) {
                comment.setStatus(CommentStatus.CBU);
            } else {
                comment.setStatus(CommentStatus.CBM);
            }
        }
    }

    protected abstract E mapDtoToEntity(CommentDTO dto);

    protected abstract void mapDtoToEntity(CommentDTO dto, E entity);

    private CommentViewDTO mapCommentToView(Comment c, int level) {
        return new CommentViewDTO(c.getId(), level, c.getComment(), c.getStatus(), c.getCommentDate(), c.getChangedDate(),
                c.getAuthor().getName(), c.getAuthor().getAvatar());
    }

    /**
     * Строит дерево комментариев.
     *
     * @param comments список комментариев.
     * @return построенное дерево комментариев.
     */
    private List<CommentViewDTO> buildTree(List<E> comments) {
        if (comments.isEmpty()) {
            return Collections.emptyList();
        }
        List<CommentViewDTO> tree = new ArrayList<>();
        List<E> parentLevel = comments.stream()
                .filter(e -> e.getParent() == null) //null - начальный уровень дерева
                .collect(Collectors.toList());
        parentLevel.stream().forEach((e) -> {
            buildTreeBranch(tree, e, 0);
        });
        return tree;
    }

    /**
     * Рекурсивно строит ветку дерева комментариев.
     *
     * @param tree ссылка на дерево.
     * @param e родительский комментарий.
     * @param level уровень дерева.
     */
    private void buildTreeBranch(List<CommentViewDTO> tree, E e, int level) {
        tree.add(mapCommentToView(e, level));
        if (!e.getChilds().isEmpty()) {
            e.getChilds().stream().forEach((child) -> {
                buildTreeBranch(tree, child, level + 1);
            });
        }
    }
}
