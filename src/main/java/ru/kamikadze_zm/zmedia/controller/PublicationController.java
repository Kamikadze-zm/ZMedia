package ru.kamikadze_zm.zmedia.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kamikadze_zm.zmedia.model.dto.CommentDTO;
import ru.kamikadze_zm.zmedia.model.dto.CommentViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.PublicationDTO;
import ru.kamikadze_zm.zmedia.model.dto.PublicationViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.PublicationsPageDTO;
import ru.kamikadze_zm.zmedia.service.CommentService;
import ru.kamikadze_zm.zmedia.service.PublicationService;

@RestController
@PreAuthorize("hasAnyAuthority('ADMIN', 'MODER')")
public abstract class PublicationController<S extends PublicationService<?, D, ?, ?>, D extends PublicationDTO, CS extends CommentService<?, ?>> {

    private final S publicationService;
    private final CS commentService;

    public PublicationController(S publicationService, CS commentService) {
        this.publicationService = publicationService;
        this.commentService = commentService;
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "/")
    public PublicationsPageDTO page(@RequestParam(value = "page", required = false) Integer pageNumber) {
        if (pageNumber == null) {
            pageNumber = 1;
        }
        return publicationService.getPublicationsPage(pageNumber);
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "/{id}")
    public PublicationViewDTO details(@PathVariable("id") Integer publicationId) {
        return publicationService.getDetails(publicationId);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Integer> add(@Valid @RequestBody D dto) {
        return new ResponseEntity<>(publicationService.add(dto), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") Integer publicationId, @Valid @RequestBody D dto) {
        publicationService.update(publicationId, dto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer publicationId) {
        publicationService.deleteById(publicationId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "/{id}/comments")
    public List<CommentViewDTO> getComments(@PathVariable("id") Integer publicationId) {
        return commentService.<List<CommentViewDTO>>getCommentsTreeByPublicationId(publicationId);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/{id}/comments")
    public ResponseEntity addComment(@PathVariable("id") Integer publicationId, @Valid @RequestBody CommentDTO dto) {
        commentService.add(publicationId, dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/comments/{id}")
    public ResponseEntity updateComment(@PathVariable("id") Integer commentId, @Valid @RequestBody CommentDTO dto) {
        commentService.update(commentId, dto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(path = "/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable("id") Integer commentId, @RequestParam(value = "real", required = false) Boolean realDelete) {
        if (realDelete == null || !realDelete) {
            commentService.markDeleted(commentId);
        } else {
            commentService.deleteById(commentId);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
