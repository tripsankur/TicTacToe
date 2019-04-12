package com.trips.ankur.nab.project.tictactoe.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.trips.ankur.nab.project.tictactoe.DTO.ActionDTO;
import com.trips.ankur.nab.project.tictactoe.DTO.CreateActionDTO;
import com.trips.ankur.nab.project.tictactoe.domain.Action;
import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.domain.Position;

import com.trips.ankur.nab.project.tictactoe.service.ActionService;
import com.trips.ankur.nab.project.tictactoe.service.GameService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * Action Controller Rest Interface
 * 
 * @author tripaank
 *
 */

@RestController
@RequestMapping("/actions")
@Api(value="Action Controller", consumes="application/json", produces="application/json")  
public class ActionController {

	
    @Autowired
    private ActionService actionService;

    @Autowired
    private GameService gameService;

    
    
    Logger logger = LoggerFactory.getLogger(ActionController.class);
    
    
    /**
     * Action Creation for the game. 
     * 
     * @param gameId
     * @param player
     * @param createActionDTO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "Create an action", response = Action.class)
    @RequestMapping(value = "/{gameId}/{player}", method = RequestMethod.POST)
    public Action createMove(@PathVariable Long  gameId,@PathVariable String  player, @RequestBody CreateActionDTO createActionDTO) throws Exception {
        logger.info("Actions to insert:" + createActionDTO.getBoardRow() +","+ createActionDTO.getBoardColumn());
        Game game = gameService.getGame(gameId);
        Action action = actionService.createAction(gameService.getGame(gameId), player ,createActionDTO);
   
        gameService.updateGameStatus(gameService.getGame(gameId), actionService.checkCurrentGameStatus(game));
        gameService.updateGameTurn(gameService.getGame(gameId),actionService.checkNextTurn(game));

		return action;
    }
    
    /**
     * Action creation for Computer AI. It creates the Auto Action for AI player. 
     * 
     * @param gameId
     * @return
     */

    @ApiOperation(value = "Automatic action for computer", response = Action.class)
    @RequestMapping(value = "/auto/{gameId}", method = RequestMethod.GET)
    public Action autoCreateAction(@PathVariable Long  gameId) {
    	
    	logger.info("AUTO action to insert:" );   
    	
    	Action action = actionService.createAutoAction(gameService.getGame(gameId));
        Game game = gameService.getGame(gameId);
        gameService.updateGameStatus(gameService.getGame(gameId), actionService.checkCurrentGameStatus(game));
        gameService.updateGameTurn(gameService.getGame(gameId),actionService.checkNextTurn(game));
     
        return action;         
    }
    
    /**
     * This method list out all the action performed in the game.
     * 
     * @param gameId
     * @return
     */

    @ApiOperation(value = "Get All actions performed in a game.")
    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public List<ActionDTO> getActionsInGame(@PathVariable Long  gameId) {
		return actionService.getActionsInGame(gameService.getGame(gameId));
    }

    /**
     * Returns the actions performed by the player in a game.
     * 
     * @param gameId
     * @param player
     * @return
     */
    
    @ApiOperation(value = "Get All actions performed in a game by the player", notes="Take the gameId and Player as input and generate the list of Actions/Moves.")
    @RequestMapping(value = "/check/{gameId}/{player}", method = RequestMethod.GET)
    public List<Position> validateMoves(@PathVariable Long  gameId, @PathVariable String  player) {
    	return actionService.getPlayerActionPositionsInGame(gameService.getGame(gameId), player);
    }
    

    /**
     * Validates if it is the turn of the player. (Not required as the create action validates it self)
     * 
     * @param gameId
     * @param player
     * @return
     */
    @ApiOperation(value = "Validates if is the turn of the player or not.")
    @RequestMapping(value = "/turn/{gameId}/{player}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isPlayerTurn(@PathVariable Long  gameId, @PathVariable String  player) {
    	return actionService.isPlayerTurn(gameService.getGame(gameId), player);
    }

    /**
     * This returns all the available positions for the game. (Not required as the Game itself contains the positions in the update)
     * 
     * @param gameId
     * @return
     */
    @ApiOperation(value = "Returns all the available position for a game.")
    @RequestMapping(value = "/available/{gameId}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Position> availablePosition(@PathVariable Long  gameId) {
    	return actionService.getOpenPosition(gameService.getGame(gameId));
    }

  }
