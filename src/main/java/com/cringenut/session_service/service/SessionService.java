package com.cringenut.session_service.service;

import com.cringenut.session_service.model.Player;
import com.cringenut.session_service.model.Session;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final CacheManager cacheManager;

    public SessionService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @CachePut(value = "SESSION_CACHE", key = "#ownerId")
    public ResponseEntity<Session> createSession(Integer size, Integer ownerId) {
        Session session = new Session();
        session.setPlayers(new Player[size]);
        session.setOwnerId(ownerId);

        Cache sessionCache = cacheManager.getCache("SESSION_CACHE");
        sessionCache.put(ownerId, session);

        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }
}
