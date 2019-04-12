package com.trips.ankur.nab.project.tictactoe.service.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.trips.ankur.nab.project.tictactoe.DTO.CreateActionDTO;
import com.trips.ankur.nab.project.tictactoe.domain.Board;
import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.domain.Position;
import com.trips.ankur.nab.project.tictactoe.enums.GameStatus;
import com.trips.ankur.nab.project.tictactoe.enums.GameType;
import com.trips.ankur.nab.project.tictactoe.enums.State;
import com.trips.ankur.nab.project.tictactoe.enums.Turn;



import static java.util.Arrays.asList;


/**
 * GameLogic class. Game logic for the player vs player games are defined here.
 * 
 * @author tripaank
 *
 */

public class GameLogic {
    private final Game game;
    public static final String COMPUTER = "COMPUTER";

    public GameLogic(Game game) {
        this.game = game;
    }

    /**
     * Checks if the player wins
     * @param positions Board positions from player moves retrieved from database
     * @return true or false if the player is a winner
     */
    public static boolean isWinner(List<Position> positions) {
        return getWinningPositions().stream().anyMatch(positions::containsAll);
    }

    /**
     * Stores list of winning positions.
     * @return the list of the winning position's list
     */
    static final List<List<Position>> getWinningPositions() {
        List<List<Position>> winingPositions = new ArrayList<>();

        winingPositions.add(asList(new Position(1, 1), new Position(1, 2), new Position(1,3)));
        winingPositions.add(asList(new Position(2, 1), new Position(2, 2), new Position(2,3)));
        winingPositions.add(asList(new Position(3, 1), new Position(3, 2), new Position(3,3)));

        winingPositions.add(asList(new Position(1, 1), new Position(2, 1), new Position(3,1)));
        winingPositions.add(asList(new Position(1, 2), new Position(2, 2), new Position(3,2)));
        winingPositions.add(asList(new Position(1, 3), new Position(2, 3), new Position(3,3)));

        winingPositions.add(asList(new Position(1, 1), new Position(2, 2), new Position(3,3)));
        winingPositions.add(asList(new Position(3, 1), new Position(2, 2), new Position(1,3)));

        return winingPositions;
    }

    /**
     * Stores all pairs of available rows and columns
     * @return list of all board's positions
     */
    public static ArrayList<Position> getAllPositions() {
    	ArrayList<Position> positions = new ArrayList<>();
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                positions.add(new Position(row, col));
            }
        }
        return positions;
    }

    /**
     *
     * @param numberOfFirstPlayerMovesInGame
     * @param numberOfSecondPlayerMovesInGame
     * @return true or false depending on the count of the player's moves
     */
    static boolean playerTurn(int numberOfFirstPlayerMovesInGame, int numberOfSecondPlayerMovesInGame) {
        return numberOfFirstPlayerMovesInGame == numberOfSecondPlayerMovesInGame || numberOfFirstPlayerMovesInGame == 0;
    }

    /**
     * Return if the board is full or not.
     * 
     * @param takenPositions
     * @return
     */
    public static boolean isBoardIsFull(List<Position> takenPositions) {
        return takenPositions.size() == 9;
    }

    /**
     * validates if the move is correct or not. Throws exception if incorrect moves.
     * 
     * @param boardColumn
     * @param boardRow
     * @param positions
     * @throws Exception
     */
	public static void validateMove(int boardColumn, int boardRow, List<Position> positions) throws Exception {
		if(boardColumn > 3 || boardColumn <1 || boardRow >3 || boardColumn <1)
			throw new IllegalStateException("Board Row and Column should be in the range of 1 to 3.");
		
		Position p =new Position(boardRow, boardColumn);
		if(positions.stream().anyMatch(position -> position.equals(p)))
			throw new IllegalStateException("This position(row,column) is already occupied.");
		
	}

	/**
	 * Validates the Move before the create action.
	 * 
	 * @param game
	 * @param player
	 * @param createActionDTO
	 * @param positions
	 * @throws Exception
	 */
	public static void validateBeforeCreate(Game game, String player, CreateActionDTO createActionDTO, List<Position> positions) throws Exception {
		//Validate if the Player is a part of the game
		if(!isPlayeraPartOfTheGame(game,player))
					throw new IllegalStateException(player + " is not part of the Game. Allowed players are : "+game.getPlayer1()+", and "+game.getPlayer2()) ; 
			
		//Validate if game is still waiting for players
		if(game.getGameStatus()==GameStatus.WAITS_FOR_PLAYER)
			throw new IllegalStateException("Can not place a move, Still waiting for the Second Player."); 

		//Validate if Game is not finished
		if(isGameOver(game)) 
			throw new IllegalStateException("Game is Finished, Status :"+game.getGameStatus()); 
		
		//Validate if the game is "IN_PROGRESS"
		if(game.getGameStatus()!=GameStatus.IN_PROGRESS)
			throw new IllegalStateException("Can not place a move, game is not running. Current Status: "+game.getGameStatus()); 

		//Validate if it is the turn of the player
		if(!isPlayerTurn(game,player))
			throw new IllegalStateException("Not your Turn, "+game.getTurn()+" need to place his moves."); 
		
		//Validate if the move is correct
		validateMove(createActionDTO.getBoardColumn(), createActionDTO.getBoardRow(), positions);
		
	}

	/**
	 * Validates if the player is a part of the game or not
	 * 
	 * @param game2
	 * @param player
	 * @return
	 */
	private static boolean isPlayeraPartOfTheGame(Game game2, String player) {

		if(game2.getPlayer1().equals(player)||game2.getPlayer2().equals(player))
			return true;
		else return false;
	}

	/**
	 * Validates if it is the turn of the player.
	 * 
	 * @param game
	 * @param player
	 * @return
	 */
	public static boolean isPlayerTurn(Game game, String player) {
		if(game.getTurn() == Turn.FIRST_PLAYER && game.getPlayer1().equals(player))
			return true;
		else if(game.getTurn() == Turn.SECOND_PLAYER && game.getPlayer2().equals(player))
			return true;
		return false;
	}

	/**
	 * Validates if the game is empty or not.
	 * 
	 * @param game
	 * @return
	 */
	public static Boolean isGameOver(Game game) {
		return game.getGameStatus() == GameStatus.TIE 
				|| game.getGameStatus() ==GameStatus.FIRST_PLAYER_WON
				|| game.getGameStatus() ==GameStatus.SECOND_PLAYER_WON;
		
	}
	
/**
 * Validates before joining the game.
 * 
 * @param player
 * @param game
 */
	public static void validateBeforeJoiningGame(String player, Game game) {
		if(game.getGameStatus()!=GameStatus.WAITS_FOR_PLAYER)
			throw new IllegalStateException("Game is Not Waiting for Players, Status :"+game.getGameStatus());
		if(game.getGameType() != GameType.COMPETITION)
			throw new IllegalStateException("Can not Join a game which is not of Type COMPETITION, Type is: "+game.getGameType());
		if(game.getPlayer1().equals(player))
			throw new IllegalStateException("Can not Join a game with the same player name for both players.");
	}
	
	
	/**
	 * Update board method.
	 * 
	 * @param boardRow
	 * @param boardColumn
	 * @param game
	 * @param player
	 * @param position
	 * @return
	 */
	public static Board updateBoard(int boardRow, int boardColumn, Game game, String player,List<Position> position) {
		Board b = game.getBoard();
		State[][] curr_board= b.getCurrent_board();
		if(game.getPlayer1().equals(player))
			curr_board[boardRow-1][boardColumn-1] = State.X;
		else
			curr_board[boardRow-1][boardColumn-1] = State.O;
		b.setCurrent_board(curr_board);
		
		b.setMovesAvailable(GameLogic.getOpenPositions(position));
		
		return b;
	}
	

/**
 * Returns all the open positions in the game.
 * 
 * @param takenPositions
 * @return
 */
    public static ArrayList<Position> getOpenPositions(List<Position> takenPositions) {
        return  (ArrayList<Position>) getAllPositions().stream().filter(p -> !takenPositions.contains(p)).collect(Collectors.toList());
   }
    
    
    
/**
 * This generates the Auto Action. Uses the MiniMax Algorithm.
 * 
 * @param game
 * @return
 */
    public static Position nextAutoAction(Game game) {
    	return MiniMax.run(game.getPlayer2(), game, Double.POSITIVE_INFINITY);
   }

    /**
     * Perform Validations before the Computer Action. 
     * 
     * @param game
     */
	public static void valdateBeforeComputerAction(Game game) {
		if(game.getGameType() != GameType.COMPUTER)
			throw new IllegalStateException("Illegal Action, Auto Action is only available for "+GameType.COMPUTER);
		
		if( !game.getPlayer2().equals(COMPUTER))
			throw new IllegalStateException("Not the turn of Computer, Auto Action should not be triggered.");
		
		//Validate if Game is not finished
		if(isGameOver(game)) 
			throw new IllegalStateException("Game is Finished, Status :"+game.getGameStatus()); 
		
		//Validate if the game is "IN_PROGRESS"
		if(game.getGameStatus()!=GameStatus.IN_PROGRESS)
			throw new IllegalStateException("Can not place a move, game is not running. Current Status: "+game.getGameStatus());
		
		//Validate if it is the turn of the player
		if(!isPlayerTurn(game,game.getPlayer2()))
			throw new IllegalStateException("Not your Turn, "+game.getTurn()+" need to place his moves."); 
	}

}
