package ru.kamikadze_zm.zmedia.service;

import org.springframework.security.access.AccessDeniedException;
import ru.kamikadze_zm.zmedia.model.dto.PublicationDTO;
import ru.kamikadze_zm.zmedia.model.dto.PublicationShortViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.PublicationViewDTO;
import ru.kamikadze_zm.zmedia.model.dto.PublicationsPageDTO;
import ru.kamikadze_zm.zmedia.model.entity.Publication;

public interface PublicationService<E extends Publication, D extends PublicationDTO, SV extends PublicationShortViewDTO, FV extends PublicationViewDTO> {

    public PublicationsPageDTO getPublicationsPage(int page);

    public long getCount();

    public Integer add(D dto);

    public void update(Integer id, D dto) throws AccessDeniedException;

    public void deleteById(Integer id) throws AccessDeniedException;

    /**
     * Возвращает публикацию и увеличивает счетчик просмотров.
     *
     * @param id id публикации.
     * @return публикацию.
     */
    public FV getDetails(Integer id);

    public E load(Integer id);
}
