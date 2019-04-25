package ru.kamikadze_zm.zmedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kamikadze_zm.zmedia.model.StreamInfo;
import ru.kamikadze_zm.zmedia.service.OnlineService;

@RestController
@RequestMapping("/api/online/")
@PreAuthorize("isAuthenticated()")
public class OnlineController {

    @Autowired
    private OnlineService onlineService;

    @GetMapping
    public ResponseEntity<StreamInfo> getStreamInfo() {
        return new ResponseEntity<>(onlineService.getStreamInfo(), HttpStatus.OK);
    }
}
