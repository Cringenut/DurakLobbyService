package com.cringenut.lobby_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lobby {

    private Integer ownerId;
    private Integer[] playerIds;

}
