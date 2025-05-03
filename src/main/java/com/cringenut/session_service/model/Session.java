package com.cringenut.session_service.model;

import lombok.Data;

import java.util.List;

@Data
public class Session {

    private Integer ownerId;
    private Player[] players;

}
