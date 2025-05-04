package com.cringenut.lobby_service.controller;

import com.cringenut.lobby_service.model.Lobby;
import com.cringenut.lobby_service.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lobby")
public class LobbyController {

    @Autowired
    private LobbyService lobbyService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Lobby createLobby(@RequestParam Integer size, @RequestParam Integer ownerId) {
        return lobbyService.createLobby(size, ownerId);
    }

    @GetMapping("/{lobbyId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Lobby getLobby(@PathVariable Integer lobbyId) {
        return lobbyService.getLobby(lobbyId);
    }

    @DeleteMapping("/{lobbyId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteLobby(@PathVariable Integer lobbyId) {
        lobbyService.deleteLobby(lobbyId);
    }

    @PostMapping("/{lobbyId}/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Lobby joinLobby(@PathVariable Integer lobbyId, @PathVariable Integer userId) {
        return lobbyService.joinLobby(lobbyId, userId);
    }

}
