package com.devsuperior.dslist.repositories;

import com.devsuperior.dslist.entites.Gamelist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameListRepository extends JpaRepository<Gamelist, Long> {

}
