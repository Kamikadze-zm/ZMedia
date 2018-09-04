package ru.kamikadze_zm.zmedia.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.TopicManagementResponse;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kamikadze_zm.zmedia.exception.NotificationsException;
import ru.kamikadze_zm.zmedia.model.entity.NotificationUser;
import ru.kamikadze_zm.zmedia.model.entity.Publication;
import ru.kamikadze_zm.zmedia.model.entity.util.PublicationType;
import ru.kamikadze_zm.zmedia.repository.NotificationUserRepository;
import ru.kamikadze_zm.zmedia.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOG = LogManager.getLogger(NotificationServiceImpl.class);

    private static final String TOPIC_NAME = "novelties";

    @Autowired
    private NotificationUserRepository notificationUserRepository;
    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @Override
    @Transactional
    public void saveToken(String token) throws NotificationsException {
        try {
            TopicManagementResponse response = firebaseMessaging.subscribeToTopic(Arrays.asList(token), TOPIC_NAME);
            if (response.getSuccessCount() == 1) {
                notificationUserRepository.save(new NotificationUser(token));
            } else {
                throw new NotificationsException("Не удалось оформить подписку, повторите попытку");
            }
        } catch (FirebaseMessagingException e) {
            LOG.error("Subscribe to topic exception: ", e);
            throw new NotificationsException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteToken(String token) {
        if (notificationUserRepository.existsById(token)) {
            notificationUserRepository.deleteById(token);
        }
    }

    @Override
    @Async
    public void sendNotification(Publication publication) {
        PublicationType publicationType = PublicationType.identifyType(publication);
        String title;
        switch (publicationType) {
            case FILM:
                title = "Добавлен фильм";
                break;
            case TV_SERIES:
                title = "Добавлен сериал";
                break;
            case GAME:
                title = "Добавлена игра";
                break;
            default:
                title = "Добавлена публикация";
        }
        sendNotification(publication, publicationType, title);
    }

    @Override
    @Async
    public void sendNotificationForUpdated(Publication publication) {
        PublicationType publicationType = PublicationType.identifyType(publication);
        String title;
        switch (publicationType) {
            case FILM:
                title = "Обновлен фильм";
                break;
            case TV_SERIES:
                title = "Обновлен сериал";
                break;
            case GAME:
                title = "Обновлена игра";
                break;
            default:
                title = "Обновлена публикация";
        }
        sendNotification(publication, publicationType, title);
    }

    private void sendNotification(Publication p, PublicationType type, String title) {
        Message message = Message.builder()
                .setTopic(TOPIC_NAME)
                .putData("title", title)
                .putData("body", p.getHeader())
                .putData("icon", p.getCoverLink())
                .putData("type", type.toString())
                .putData("id", p.getId().toString())
                .build();
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            LOG.warn("Could not send notification: ", e);
        }
    }
}
