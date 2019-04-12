package com.trips.ankur.nab.project.tictactoe.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.domain.Action;

import java.util.List;

/**
 * 
 * @author tripaank
 *
 */
@Repository
public interface ActionRepository extends CrudRepository<Action, Long> {

    List<Action> findByGame(Game game);
    List<Action> findByGameAndPlayer(Game game, String player);
    int countByGameAndPlayer(Game game, String player);
	
}
