package com.cringenut.session_service.controller;

import com.cringenut.session_service.model.Session;
import com.cringenut.session_service.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/{gameId}")
    public ResponseEntity<Session> getSessionStatus(String gameId) {
        return sessionService.getSessionStatus(gameId);
    }

}
