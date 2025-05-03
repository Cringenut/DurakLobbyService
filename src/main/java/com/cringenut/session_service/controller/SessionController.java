package com.cringenut.session_service.controller;

import com.cringenut.session_service.model.Session;
import com.cringenut.session_service.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public ResponseEntity<Session> createSession(@RequestParam Integer size, @RequestParam Integer ownerId) {
        return sessionService.createSession(size, ownerId);
    }


}
