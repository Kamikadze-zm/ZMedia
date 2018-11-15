package ru.kamikadze_zm.zmedia.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.kamikadze_zm.zmedia.model.entity.User;
import ru.kamikadze_zm.zmedia.model.entity.VerificationCode;
import ru.kamikadze_zm.zmedia.service.EmailService;
import ru.kamikadze_zm.zmedia.service.VerificationCodeService;
import ru.kamikadze_zm.zmedia.util.FreeMarkerEmailTemplates;
import ru.kamikadze_zm.zmedia.util.FreeMarkerEmailTemplates.TemplateType;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = LogManager.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private FreeMarkerEmailTemplates emailTemplates;

    @Autowired
    VerificationCodeService verificationCodeService;

    @Value("${mail.from.noreply}")
    private String fromNoReply;

    @Override
    @Async
    public void sendEmailConfirmationMessage(User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("code", verificationCodeService.createAndSave(user.getEmail(), VerificationCode.CodeType.EMAIL).getCode());

        emailTemplates.getTemplateAsString(TemplateType.EMAIL_CONFIRMATION, model)
                .ifPresent(html -> sendMessage(fromNoReply, user.getEmail(), "Подтверждение почты для ZMedia", html));
    }

    @Override
    @Async
    public void sendPasswordRestoringMessage(User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("code", verificationCodeService.createAndSave(user.getEmail(), VerificationCode.CodeType.PASSWORD).getCode());

        emailTemplates.getTemplateAsString(TemplateType.PASSWORD_RESTORING, model)
                .ifPresent(html -> sendMessage(fromNoReply, user.getEmail(), "Восстановление пароля для ZMedia", html));
    }

    private void sendMessage(String from, String to, String subject, String html) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
        try {
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
        } catch (MessagingException e) {
            LOG.error("Create email message exception: ", e);
            return;
        }
        try {
            emailSender.send(message);
        } catch (MailException e) {
            LOG.error("Email sending exception: ", e);
        }
    }
}
