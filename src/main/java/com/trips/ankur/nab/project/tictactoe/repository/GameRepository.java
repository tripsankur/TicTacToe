package com.trips.ankur.nab.project.tictactoe.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.enums.GameStatus;
import com.trips.ankur.nab.project.tictactoe.enums.GameType;

import java.util.List;

/**
 * 
 * @author tripaank
 *
 */
@Repository
public interface GameRepository extends CrudRepository<Game, Long>{
    List<Game> findByGameTypeAndGameStatus(GameType GameType, GameStatus GameStatus);
    List<Game> findByGameStatus(GameStatus gameStatus);
}
