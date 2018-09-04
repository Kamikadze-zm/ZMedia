package ru.kamikadze_zm.zmedia.service;

import ru.kamikadze_zm.zmedia.exception.NotificationsException;
import ru.kamikadze_zm.zmedia.model.entity.Publication;

public interface NotificationService {

    public void saveToken(String token) throws NotificationsException;

    public void deleteToken(String token);

    public void sendNotification(Publication publication);

    public void sendNotificationForUpdated(Publication publication);
}
