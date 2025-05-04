package com.cringenut.lobby_service.service;

import com.cringenut.lobby_service.model.Lobby;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.IntStream;

@Service
public class LobbyService {

    private final CacheManager cacheManager;

    public LobbyService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @CachePut(value = "LOBBY_CACHE", key = "#ownerId")
    public Lobby createLobby(Integer size, Integer ownerId) {
        Lobby lobby = new Lobby();
        Integer[] playerIds = new Integer[size];
        playerIds[0] = ownerId; // Make owner the first player

        lobby.setPlayerIds(playerIds);
        lobby.setOwnerId(ownerId);

        Cache lobbyCache = cacheManager.getCache("LOBBY_CACHE");
        lobbyCache.put(ownerId, lobby);

        return lobby;
    }

    @Cacheable(value = "LOBBY_CACHE", key = "#lobbyId")
    public Lobby getLobby(Integer lobbyId) {
        Cache lobbyCache = cacheManager.getCache("LOBBY_CACHE");
        return lobbyCache.get(lobbyId, Lobby.class);
    }

    @CacheEvict(value = "LOBBY_CACHE", key = "#lobbyId")
    public void deleteLobby(Integer lobbyId) {
        Cache lobbyCache = cacheManager.getCache("LOBBY_CACHE");
        lobbyCache.evict(lobbyId);
    }

    @CachePut(value = "LOBBY_CACHE", key = "#lobbyId")
    public Lobby joinLobby(Integer lobbyId, Integer userId) {
        Lobby lobby = getLobby(lobbyId);
        Integer[] playerIds = lobby.getPlayerIds();

        IntStream.range(0, playerIds.length)
                .filter(i -> playerIds[i] == null)
                .findFirst()
                .ifPresent(i -> playerIds[i] = userId);
        System.out.println("Player ids: " + Arrays.toString(playerIds));
        System.out.println("Lobby ids: " + Arrays.toString(lobby.getPlayerIds()));

        Cache lobbyCache = cacheManager.getCache("LOBBY_CACHE");
        lobbyCache.put(lobbyId, lobby);

        return lobby;
    }

}
