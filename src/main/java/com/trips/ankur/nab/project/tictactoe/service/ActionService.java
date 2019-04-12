package com.trips.ankur.nab.project.tictactoe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trips.ankur.nab.project.tictactoe.DTO.ActionDTO;
import com.trips.ankur.nab.project.tictactoe.DTO.CreateActionDTO;
import com.trips.ankur.nab.project.tictactoe.domain.Action;
import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.domain.Position;
import com.trips.ankur.nab.project.tictactoe.enums.GameStatus;
import com.trips.ankur.nab.project.tictactoe.enums.GameType;
import com.trips.ankur.nab.project.tictactoe.enums.Turn;
import com.trips.ankur.nab.project.tictactoe.repository.ActionRepository;
import com.trips.ankur.nab.project.tictactoe.service.algorithms.GameLogic;

/**
 * Action Service - Service method for Action Controller 
 * @author tripaank
 *
 */

@Service
@Transactional
public class ActionService {
	private final ActionRepository actionRepository;


	@Autowired
	public ActionService(ActionRepository actionRepository) {
		this.actionRepository = actionRepository;
	}

	@Autowired
	private BoardService boardService;

	/**
	 * Creating a new action for the game.
	 * 
	 * @param game
	 * @param player
	 * @param createActionDTO
	 * @return
	 * @throws Exception
	 */
	public Action createAction(Game game, String player, CreateActionDTO createActionDTO) throws Exception {
		GameLogic.validateBeforeCreate(game,player,createActionDTO,getTakenActionPositionsInGame(game));
		
		Action action = new Action();		
		action.setBoardRow(createActionDTO.getBoardRow());
		action.setBoardColumn(createActionDTO.getBoardColumn());
		action.setCreated(new Date());
		action.setGame(game);
		action.setPlayer(player);

		actionRepository.save(action);

		boardService.updateBoard(createActionDTO.getBoardRow(), 
				createActionDTO.getBoardColumn(), game,player,
				getTakenActionPositionsInGame(game));
		return action;
	}


	/**
	 * Creates an action for AI player - This uses MiniMax algorithm.
	 * 
	 * @param game
	 * @return action
	 */
	public Action createAutoAction(Game game) {
		//GameLogic.validateBeforeCreate(game,player,createActionDTO,getTakenActionPositionsInGame(game));
		GameLogic.valdateBeforeComputerAction(game);
		Action action = new Action();
		Position pos = GameLogic.nextAutoAction(game);
		
		action.setBoardColumn(pos.getBoardColumn());
		action.setBoardRow(pos.getBoardRow());
		action.setCreated(new Date());
		action.setPlayer(game.getPlayer2());
		action.setGame(game);

		actionRepository.save(action);
		boardService.updateBoard(pos.getBoardRow(), 
				pos.getBoardColumn(), game,game.getPlayer2(),
				getTakenActionPositionsInGame(game));

		return action;
	}

	/**
	 * Return all the taken positions in the game. 
	 * 
	 * @param game
	 * @return
	 */
	private List<Position> getTakenActionPositionsInGame(Game game) {
		return actionRepository.findByGame(game).stream()
				.map(action -> new Position(action.getBoardRow(), action.getBoardColumn()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Checks the current status of the game.
	 * 
	 * @param game
	 * @return Game Status
	 */

	public GameStatus checkCurrentGameStatus(Game game) {
		if (GameLogic.isWinner(getPlayerActionPositionsInGame(game, game.getPlayer1()))) {
			return GameStatus.FIRST_PLAYER_WON;
		} else if (GameLogic.isWinner(getPlayerActionPositionsInGame(game, game.getPlayer2()))) {
			return GameStatus.SECOND_PLAYER_WON;
		} else if (GameLogic.isBoardIsFull(getTakenActionPositionsInGame(game))) {
			return GameStatus.TIE;
		} else if (game.getGameType() == GameType.COMPETITION && game.getPlayer2() == null ) {
			return GameStatus.WAITS_FOR_PLAYER;
		} else {
			return GameStatus.IN_PROGRESS;
		}

	}

	/**
	 * Return all the action in game.
	 * 
	 * @param game
	 * @return
	 */
	public List<ActionDTO> getActionsInGame(Game game) {
		List<Action> actionsInGame = actionRepository.findByGame(game);
		List<ActionDTO> actions = new ArrayList<>();

		for(Action action :  actionsInGame) {
			ActionDTO actionDTO = new ActionDTO();
			actionDTO.setBoardColumn(action.getBoardColumn());
			actionDTO.setBoardRow(action.getBoardRow());
			actionDTO.setCreated(action.getCreated());
			actionDTO.setGameStatus(action.getGame().getGameStatus());
			actionDTO.setUserName(action.getPlayer() == null ? GameType.COMPUTER.toString() : action.getPlayer() );
			actions.add(actionDTO);
		}
		return actions;
	}
	
	/**
	 * Returns the player actions in a game.
	 * 
	 * @param game
	 * @param player
	 * @return
	 */
	public List<Position> getPlayerActionPositionsInGame(Game game, String player) {
		return actionRepository.findByGameAndPlayer(game, player).stream()
				.map(move -> new Position(move.getBoardRow(), move.getBoardColumn()))
				.collect(Collectors.toList());
	}
	
	/**
	 * returns all the number of player actions.
	 * 
	 * @param game
	 * @param player
	 * @return
	 */
	public int getTheNumberOfPlayerActionsInGame(Game game, String player) {
		return actionRepository.countByGameAndPlayer(game, player);
	}

	
	/**
	 * validates if it is the turn of the player or not.
	 * 
	 * @param game
	 * @param player
	 * @return
	 */
	public boolean isPlayerTurn(Game game, String player) {
		return GameLogic.isPlayerTurn(game, player);
	}
	
	/**
	 * returns the next turn.
	 * 
	 * @param game
	 * @return
	 */
	public Turn checkNextTurn(Game game ) {
		if(game.getGameStatus() == GameStatus.IN_PROGRESS) {
			if(game.getTurn() == Turn.FIRST_PLAYER)
				return Turn.SECOND_PLAYER;
			if(game.getTurn() == Turn.SECOND_PLAYER)
				return Turn.FIRST_PLAYER;
		} else if(game.getGameStatus() == GameStatus.WAITS_FOR_PLAYER)
			return Turn.GAME_YET_TO_START;

		return Turn.GAME_FINISHED;
	}

	/**
	 * returns the open positions.
	 * 
	 * @param game
	 * @return
	 */
	public List<Position> getOpenPosition(Game game) {	
		return GameLogic.getOpenPositions(getTakenActionPositionsInGame(game));

	}

}
