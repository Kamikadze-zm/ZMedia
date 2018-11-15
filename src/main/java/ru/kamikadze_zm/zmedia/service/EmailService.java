package ru.kamikadze_zm.zmedia.service;

import ru.kamikadze_zm.zmedia.model.entity.User;

public interface EmailService {

    public void sendEmailConfirmationMessage(User user);

    public void sendPasswordRestoringMessage(User user);
}
