package ru.kamikadze_zm.zmedia.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kamikadze_zm.zmedia.model.Pagination;
import ru.kamikadze_zm.zmedia.model.dto.DownloadLinkDTO;
import ru.kamikadze_zm.zmedia.model.dto.PublicationDTO;
import ru.kamikadze_zm.zmedia.model.dto.PublicationShortViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.PublicationViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.PublicationsPageDTO;
import ru.kamikadze_zm.zmedia.model.entity.DownloadLink;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.repository.PublicationRepository;
import ru.kamikadze_zm.zmedia.service.NotificationService;
import ru.kamikadze_zm.zmedia.service.PublicationService;
import ru.kamikadze_zm.zmedia.util.AuthenticationUtil;
import ru.kamikadze_zm.zmedia.util.Constants;

@Service
public abstract class PublicationServiceImpl<E extends Publication, D extends PublicationDTO, SV extends PublicationShortViewDTO, FV extends PublicationViewDTO>
        implements PublicationService<E, D, SV, FV> {

    private final int DEFAULT_PAGE_SIZE = 15;

    private final PublicationRepository<E> publicationRepository;

    @Autowired
    private NotificationService notificationService;

    public PublicationServiceImpl(PublicationRepository<E> publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PublicationsPageDTO getPublicationsPage(int page) {
        Pagination pagination = new Pagination(page, DEFAULT_PAGE_SIZE, getCount());
        List<E> publications = publicationRepository.findAll(
                PageRequest.of(pagination.getCurrentPage() - 1, DEFAULT_PAGE_SIZE, Sort.by(Order.desc("publishDate")))).getContent();
        List<SV> publicationsDTO = new ArrayList<>();
        publications.forEach(p -> publicationsDTO.add(mapPublicationToShortView(p)));
        return new PublicationsPageDTO(publicationsDTO, pagination);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        return publicationRepository.count();
    }

    @Override
    @Transactional
    public Integer add(D dto) {
        E e = publicationRepository.save(mapDtoToEntity(dto));
        notificationService.sendNotification(e);
        return e.getId();
    }

    @Override
    @Transactional
    public void update(Integer id, D dto) throws AccessDeniedException {
        E e = publicationRepository.findById(id).get();
        if (!AuthenticationUtil.compareUserEmail(e.getAuthor().getEmail()) && !AuthenticationUtil.isAdmin()) {
            throw new AccessDeniedException(Constants.ACCESS_DENIED_MESSAGE);
        }
        Date publishDate = e.getPublishDate();
        mapDtoToEntity(dto, e);
        publicationRepository.save(e);
        if (!publishDate.equals(e.getPublishDate())) {
            notificationService.sendNotificationForUpdated(e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) throws AccessDeniedException {
        Optional<E> oe = publicationRepository.findById(id);
        if (!oe.isPresent()) {
            return;
        }
        E e = oe.get();
        if (!AuthenticationUtil.compareUserEmail(e.getAuthor().getEmail()) && !AuthenticationUtil.isAdmin()) {
            throw new AccessDeniedException(Constants.ACCESS_DENIED_MESSAGE);
        }
        publicationRepository.delete(e);
    }

    /**
     * Возвращает публикацию и увеличивает счетчик просмотров.
     *
     * @param id id публикации.
     * @return публикацию.
     */
    @Override
    @Transactional
    public FV getDetails(Integer id) {
        Optional<E> op = publicationRepository.findById(id);
        if (!op.isPresent()) {
            return null;
        }
        E p = op.get();
        p.setViewsCounter(p.getViewsCounter() + 1);
        publicationRepository.save(p);
        return mapPublicationToFullView(p);
    }

    @Override
    @Transactional
    public E load(Integer id) {
        return publicationRepository.getOne(id);
    }

    @SuppressWarnings("unchecked")
    protected void mapDtoToPublication(D dto, E publication) {
        publication.setHeader(dto.getHeader());
        publication.setName(dto.getName());
        publication.setOriginalName(dto.getOriginalName());
        publication.setReleaseYear(dto.getReleaseYear());
        publication.setGenres(dto.getGenres());
        publication.setCoverLink(dto.getCoverLink());
        publication.setFileSize(dto.getFileSize());
        publication.setDescription(dto.getDescription());
        publication.setAdditionalInfo(dto.getAdditionalInfo());
        publication.setDetails(dto.getDetails());
        if (publication.getAuthor() == null) {
            publication.setPublishDate(new Date());
            publication.setAuthor(AuthenticationUtil.getAuthenticatedUser());
        } else if ((publication.getNote() == null && dto.getNote() != null)
                || (publication.getNote() != null && !publication.getNote().equals(dto.getNote()))) {
            publication.setPublishDate(new Date());
        }
        publication.setNote(dto.getNote());
        List<DownloadLink> links = publication.getDownloadLinks();
        if (links != null) {
            links.clear();
        } else {
            links = new ArrayList<>(dto.getDownloadLinks().size());
            publication.setDownloadLinks(links);
        }
        mapDownloadLinks(publication.getDownloadLinks(), dto.getDownloadLinks());
    }

    protected abstract SV mapPublicationToShortView(E publication);

    protected abstract FV mapPublicationToFullView(E publication);

    protected abstract E mapDtoToEntity(D dto);

    protected abstract void mapDtoToEntity(D dto, E entity);

    protected abstract void mapDownloadLinks(List<? super DownloadLink> entityLinks, List<DownloadLinkDTO> dtoLinks);
}
