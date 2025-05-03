package com.cringenut.session_service.service;

import com.cringenut.session_service.model.Player;
import com.cringenut.session_service.model.Session;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class SessionService {

    private final CacheManager cacheManager;

    public SessionService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @CachePut(value = "SESSION_CACHE", key = "#ownerId")
    public Session createSession(Integer size, Integer ownerId) {
        Session session = new Session();
        Integer[] playerIds = new Integer[size];
        playerIds[0] = ownerId; // Make owner the first player

        session.setPlayerIds(playerIds);
        session.setOwnerId(ownerId);

        Cache sessionCache = cacheManager.getCache("SESSION_CACHE");
        sessionCache.put(ownerId, session);

        return session;
    }

    @Cacheable(value = "SESSION_CACHE", key = "#sessionId")
    public Session getSession(Integer sessionId) {
        Cache sessionCache = cacheManager.getCache("SESSION_CACHE");
        return sessionCache.get(sessionId, Session.class);
    }

    @CacheEvict(value = "SESSION_CACHE", key = "#sessionId")
    public void deleteSession(Integer sessionId) {
        Cache sessionCache = cacheManager.getCache("SESSION_CACHE");
        sessionCache.evict(sessionId);
    }

    @CachePut(value = "SESSION_CACHE", key = "#sessionId")
    public Session joinSession(Integer sessionId, Integer userId) {
        Session session = getSession(sessionId);
        Integer[] playerIds = session.getPlayerIds();

        IntStream.range(0, playerIds.length)
                .filter(i -> playerIds[i] == null)
                .findFirst()
                .ifPresent(i -> playerIds[i] = userId);
        System.out.println("Player ids: " + Arrays.toString(playerIds));
        System.out.println("Session ids: " + Arrays.toString(session.getPlayerIds()));

        Cache sessionCache = cacheManager.getCache("SESSION_CACHE");
        sessionCache.put(sessionId, session);

        return session;
    }

}
