package com.cringenut.session_service.controller;

import com.cringenut.session_service.model.Session;
import com.cringenut.session_service.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Session createSession(@RequestParam Integer size, @RequestParam Integer ownerId) {
        return sessionService.createSession(size, ownerId);
    }

    @GetMapping("/{sessionId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Session getSession(@PathVariable Integer sessionId) {
        return sessionService.getSession(sessionId);
    }

    @DeleteMapping("/{sessionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable Integer sessionId) {
        sessionService.deleteSession(sessionId);
    }

    @PostMapping("/{sessionId}/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Session joinSession(@PathVariable Integer sessionId, @PathVariable Integer userId) {
        return sessionService.joinSession(sessionId, userId);
    }

}
