package com.trips.ankur.nab.project.tictactoe.service;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.trips.ankur.nab.project.tictactoe.DTO.GameDTO;
import com.trips.ankur.nab.project.tictactoe.controller.GameController;
import com.trips.ankur.nab.project.tictactoe.domain.Board;
import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.enums.GameStatus;
import com.trips.ankur.nab.project.tictactoe.enums.GameType;
import com.trips.ankur.nab.project.tictactoe.enums.Turn;
import com.trips.ankur.nab.project.tictactoe.repository.GameRepository;
import com.trips.ankur.nab.project.tictactoe.service.algorithms.GameLogic;

import lombok.extern.java.Log;

/**
 * GameService class to perform Service operations on the Game.
 * 
 * @author tripaank
 *
 */

@Service
@Transactional
public class GameService {
	private static final String COMPUTER = "COMPUTER";
	private final GameRepository gameRepository;
	Logger logger = LoggerFactory.getLogger(GameService.class);
	
	@Autowired
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	@Autowired
    private BoardService boardService;
    
	/**
	 * This method creates a New Game by taking the DTO object and assigns initial values. 
	 *   
	 * @param gameDTO
	 * 
	 * @return game object
	 */
	public Game createNewGame(GameDTO gameDTO) {	
		Game game = new Game();
		Board board=boardService.createNewBoard();
		
		game.setPlayer1(gameDTO.getFirst_player());
		game.setGameType(gameDTO.getGameType());
		game.setGameStatus(gameDTO.getGameType() == GameType.COMPUTER ? GameStatus.IN_PROGRESS :
			GameStatus.WAITS_FOR_PLAYER);
		if(gameDTO.getGameType() == GameType.COMPUTER)
			game.setPlayer2(COMPUTER);
		game.setTurn(gameDTO.getGameType() == GameType.COMPUTER ? Turn.FIRST_PLAYER : Turn.GAME_YET_TO_START);
		game.setCreated(new Date());
		game.setBoard(board);
		gameRepository.save(game);
		
		return game;

	}


	/**
	 * This method is used for returning the list of open games that can be joined by the second player.
	 * 
	 * @param player
	 * @return List of open Games
	 */
	public List<Game> getAllGamesToJoin(String player) {
		logger.info("Player Name:"+player+":Testing:"+gameRepository.findByGameTypeAndGameStatus(GameType.COMPETITION,GameStatus.WAITS_FOR_PLAYER));
		
		return gameRepository.findByGameTypeAndGameStatus(GameType.COMPETITION,
				GameStatus.WAITS_FOR_PLAYER).stream().filter(game -> !game.getPlayer1().equals(player)).collect(Collectors.toList());

	}


	/**
	 * This method returns all the games.
	 * 
	 * @return all games
	 */
	public List<Game> getAllGames() {
		List<Game> gameList = new ArrayList<Game>();
		gameRepository.findAll().forEach(gameList::add);
		return gameList;
	}

	/**
	 * This method used for joining an existing game.
	 * 
	 * @param player
	 * @param gameId
	 * @return the game.
	 */
	public Game joinGame(String player, Long gameId) {
		
		
		Game game =  getGame((long) gameId);
		GameLogic.validateBeforeJoiningGame(player, game);
		
		game.setPlayer2(player);
		game.setTurn(Turn.FIRST_PLAYER);
		game.setGameStatus(GameStatus.IN_PROGRESS);
		gameRepository.save(game);
		
		return game;
	}

	/**
	 * This method is used for updating the status of the game.
	 * 
	 * @param game
	 * @param status
	 * @return the game
	 */
	public Game updateGameStatus(Game game, GameStatus status) {
        Game g = getGame(game.getId());
		g.setGameStatus(status);
		//gameRepository.save(g);
		return g;
	}

	/**
	 * This method is used finding the game from the game id.
	 * 
	 * @param id
	 * @return the game
	 */
	public Game getGame(Long id) {
		return gameRepository.findById(id).get();
	}

	
	/**
	 * This method returns the status of the game.
	 * 
	 * @param id
	 * @return Status of the game
	 */
	public GameStatus getGameStatus(Long id) {
		return gameRepository.findById(id).get().getGameStatus();
	}
	

	/**
	 * Returns the status of the game.
	 * 
	 * @param id
	 * @return
	 */
	public GameStatus getGameWinner(Long id) {
		
		return gameRepository.findById(id).get().getGameStatus();
	}

	/**
	 * updates the turn in the game objet.
	 * 
	 * @param game
	 * @param checkNextTurn
	 * @return
	 */
	public Game updateGameTurn(Game game, Turn checkNextTurn) {
        Game g = getGame(game.getId());
		g.setTurn(checkNextTurn);
		
		return g;
	}

	/**
	 * Updates the game.
	 * 
	 * @param game
	 * @param turn
	 * @param gameStatus
	 */
	public void updateGame(Game game, Turn turn, GameStatus gameStatus) {
		updateGameStatus(game, gameStatus);
		updateGameTurn(game, turn);		
	}


}
