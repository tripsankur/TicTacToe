package com.trips.ankur.nab.project.tictactoe.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.trips.ankur.nab.project.tictactoe.DTO.GameDTO;
import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.enums.GameStatus;
import com.trips.ankur.nab.project.tictactoe.service.GameService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * this is the game controller rest interface. 
 * 
 * @author tripaank
 *
 */


@RestController
@RequestMapping("/games")
@Api(value="Game Controller", consumes="application/json", produces="application/json") 
public class GameController {
	
	@Autowired
	private GameService gameService;
	Logger logger = LoggerFactory.getLogger(GameController.class);
	
	/*
	 * @ApiOperation(value = "Lists all Games", response = List.class)
	 * 
	 * @RequestMapping(method=RequestMethod.GET, value = "/hello") public String
	 * getHello() { return "Hello"; }
	 */

	
	/**
	 * This method creates a new game.
	 * 
	 * @param gameDTO
	 * @return
	 */
	@ApiOperation(value = "Creates a New Game", response = Game.class)
	@RequestMapping(method=RequestMethod.POST, value="/game")
	public Game addGame(@RequestBody GameDTO gameDTO){
		logger.info(gameDTO.toString());
		Game game = gameService.createNewGame(gameDTO);
		//logger.info("new game id: " + game.getId() +", game Player 1: "+game.getPlayer1() +", game status: "+game.getGameStatus());
		return game;
	}

	
	
	/**
	 * This method returns all the games from Database.
	 * 
	 * @return List of games
	 */
	@ApiOperation(value = "Lists all Games")
	@RequestMapping(method=RequestMethod.GET, value = "")
    public List<Game> getAllGames() {
		return gameService.getAllGames();
    }
	
	
	/**
	 * This method returns the list of games which are waiting for the second player.
	 * 
	 * @param player
	 * @return
	 */
	
	@ApiOperation(value = "Lists all Games which are waiting for Second Player")
	@RequestMapping(method=RequestMethod.GET, value = "/{player}")
    public List<Game> getAllGamesToJoin(@PathVariable String player) {
		return gameService.getAllGamesToJoin(player);
    }
	
	
	/**
	 * This method is used for Joining the game. Second player will join the existing game using this method.
	 * @param player
	 * @param gameId
	 * @return
	 */
	
	@ApiOperation(value = "Joins an existing Game", response = Game.class)
    @RequestMapping(method = RequestMethod.POST, value = "/join/{gameId}/{player}")
    public Game joinGame(@PathVariable String player, @PathVariable Long gameId) {
        Game game = gameService.joinGame( player, gameId);
        return game;
    }


	
	/**
	 * This method is used for viewing the game. Game will contain all the details.
	 * 
	 * @param id
	 * @return
	 */
	
	@ApiOperation(value = "View the game by ID", response = Game.class)
	@RequestMapping(method=RequestMethod.GET, value = "/game/{id}")
    public Game getGame(@PathVariable Long id) {
        return gameService.getGame(id);
    }
	
	
	/**
	 * This is to find out the status of an game.
	 * 
	 * @param id
	 * @return
	 */

	@ApiOperation(value = "View the Status of the game by ID", response = GameStatus.class)
	@RequestMapping(method=RequestMethod.GET, value = "/game/{id}/status")
    public GameStatus getGameStatus(@PathVariable Long id) {
        return gameService.getGameStatus(id);
    }
	
	/**
	 * Find out the winner of the game.
	 * 
	 * @param id
	 * @return the name of winner or the Status of the game.
	 */
	@ApiOperation(value = "Find out the winner of the game by ID", response = GameStatus.class)
	@RequestMapping(method=RequestMethod.GET, value = "/game/{id}/winner")
    public String getWinner(@PathVariable Long id) {
        if(gameService.getGameStatus(id)==GameStatus.FIRST_PLAYER_WON) return gameService.getGame(id).getPlayer1();
        else if(gameService.getGameStatus(id)==GameStatus.SECOND_PLAYER_WON) return gameService.getGame(id).getPlayer2();
        
        else return gameService.getGameStatus(id).toString();
    }
	
	
	
}
