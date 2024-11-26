package com.devsuperior.dslist.entites;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_game_list")
public class Gamelist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;




    public Gamelist() {
    }

    public Gamelist(long id, String name) {
        this.id = id;
        this.name = name;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gamelist gamelist = (Gamelist) o;
        return id == gamelist.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
