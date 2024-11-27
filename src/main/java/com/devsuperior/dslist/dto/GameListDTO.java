package com.devsuperior.dslist.dto;

import com.devsuperior.dslist.entites.Gamelist;

public class GameListDTO {

    private long id;
    private String name;




    public GameListDTO() {
    }

    public GameListDTO(Gamelist entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
