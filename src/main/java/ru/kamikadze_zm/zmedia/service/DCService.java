package ru.kamikadze_zm.zmedia.service;

import ru.kamikadze_zm.zmedia.model.entity.DownloadLink;
import ru.kamikadze_zm.zmedia.model.entity.Publication;

public interface DCService {

    public void updateMagnets(Publication<?, ?, DownloadLink> p);
}
