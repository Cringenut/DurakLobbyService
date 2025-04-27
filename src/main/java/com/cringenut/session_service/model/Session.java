package com.cringenut.session_service.model;

import lombok.Data;

import java.util.List;

@Data
public class Session {

    private List<Player> players;
    private Deck deck;

}
