package ru.kamikadze_zm.zmedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kamikadze_zm.zmedia.service.NotificationService;

@RestController
@RequestMapping("/api/notifications/")
public class NotificationsController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping(path = "token")
    public ResponseEntity saveToken(@RequestBody String token) {
        notificationService.saveToken(token);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "token/{token}")
    public ResponseEntity deleteToken(@PathVariable String token) {
        notificationService.deleteToken(token);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
