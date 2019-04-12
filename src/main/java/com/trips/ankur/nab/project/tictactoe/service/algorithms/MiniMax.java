/**
 * 
 */
package com.trips.ankur.nab.project.tictactoe.service.algorithms;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.trips.ankur.nab.project.tictactoe.domain.Board;
import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.domain.Position;
import com.trips.ankur.nab.project.tictactoe.enums.GameStatus;
import com.trips.ankur.nab.project.tictactoe.enums.State;
import com.trips.ankur.nab.project.tictactoe.enums.Turn;

/**
 * This Class is to perform MiniMax algorithm on the given Game. 
 * It creates a copy of the game and perform modifications to validate all possible actions and then returns the position.
 * 
 * run method is the start of the process.
 * 
 * 
 * @author tripaank
 *
 */
public class MiniMax {
	private static double maxPly;
	private static Logger logger = LoggerFactory.getLogger(MiniMax.class);
	static final int BOARD_WIDTH = 3;
	int count = 0;

	/**
	 * This is the method to execute the algorithm.
	 * 
	 * @param player the player that the AI will identify as
	 * @param board  the Tic Tac Toe board to play on
	 * @param maxPly the maximum depth
	 */

	static Position run(String player, Game game, double maxPlyTMp) {
		if (maxPlyTMp < 1) {
			throw new IllegalArgumentException("Maximum depth must be greater than 0.");
		}
		logger.info("[run]Returning to game :" + game);
		MiniMax.maxPly = maxPlyTMp;


		Game modifiedGame = getDeepCopy(game);

		MiniMaxTransferObject miniMaxTransfer = miniMax(player, modifiedGame, 0);

		/*
		 * logger.info("[run]Returning to Paernt :" + miniMaxTransfer);
		 * logger.info("[run]Returning to game :" + game);
		 * logger.info("[run]Returning to modifiedGame :" + modifiedGame);
		 */

		return miniMaxTransfer.getPosition();

	}

	/**
	 * This is the main algorithm.
	 * 
	 * @param player     the player that the AI will identify as
	 * @param game       the Tic Tac Toe board to play on
	 * @param currentPly the current depth
	 * @return the score of the board
	 */
	private static MiniMaxTransferObject miniMax(String player, Game game, int currentPly) {
		logger.info("MiniMaxTransferObject : First line player is: "+player);

		if (currentPly++ == maxPly || GameLogic.isGameOver(game) || player.isEmpty()) {
			logger.info("MiniMax Check: to return Score "+currentPly++);
			MiniMaxTransferObject tmp = new MiniMaxTransferObject();
			tmp.setScore(score(player, game));
			tmp.setGame(game);


			logger.info("MiniMax Check: to return Score "+game+", score is: "+tmp.getScore());
			return tmp;
		}


		if (GameLogic.isPlayerTurn(game, player)) {
			return getMax(player, game, currentPly);
		} else {
			return getMin(player, game, currentPly);
		}

	}

	/**
	 * Get the score of the board.
	 * 
	 * @param player the play that the AI will identify as
	 * @param board  the Tic Tac Toe board to play on
	 * @return the score of the board
	 */
	private static int score(String player, Game game) {

		logger.info("Counting Score: "+game+", test : "+player);
		//
		String opponent = (game.getPlayer1().equals(player )) ? game.getPlayer2() : game.getPlayer1();



		if (GameLogic.isGameOver(game) && didIWin(game,player)) {
			return 10;
		} else if (GameLogic.isGameOver(game) && didIWin(game,opponent)) {
			return -10;
		} else {
			return 0;
		}
	}

	/**
	 * Helper Method.
	 * To identify is the player is a winner. 
	 * 
	 * @param game
	 * @param player
	 * @return
	 */

	private static boolean didIWin(Game game, String player) {
		Boolean player1= game.getPlayer1().equals(player); 
		if(game.getGameStatus()==GameStatus.FIRST_PLAYER_WON && player1)
			return true;
		if(game.getGameStatus()==GameStatus.SECOND_PLAYER_WON && !player1)
			return true;

		return false;
	}

	/**
	 * 
	 * Play the move with the highest score.
	 * 
	 * @param player     the player that the AI will identify as
	 * @param board      the Tic Tac Toe board to play on
	 * @param currentPly the current depth
	 * @return the score of the board
	 */
	private static MiniMaxTransferObject getMax(String player, Game game, int currentPly) {
		double bestScore = Double.NEGATIVE_INFINITY;
		Position indexOfBestMove = null;
		MiniMaxTransferObject miniMaxObj = new MiniMaxTransferObject();

		for (Position theMove : game.getBoard().getMovesAvailable()) {
			Game modifiedGame = getDeepCopy(game);
			modifiedGame = updateTheGame(modifiedGame, theMove);

			int score = miniMax(player, modifiedGame, currentPly).getScore();
			if (score >= bestScore) {
				bestScore = score;
				indexOfBestMove = new Position(theMove.getBoardRow(), theMove.getBoardColumn());
			}
		}
		//logger.info("[getMax]Check indexOfBestMove: " + indexOfBestMove + ", score is : " + bestScore);

		game = updateTheGame(game, indexOfBestMove);
		miniMaxObj.setGame(game);
		miniMaxObj.setScore((int) bestScore);
		miniMaxObj.setPosition(indexOfBestMove);
		logger.info("[getMax]Check Game" + game);
		return miniMaxObj;
	}


	/**
	 * Play the move with the lowest score.
	 * 
	 * @param player     the player that the AI will identify as
	 * @param board      the Tic Tac Toe board to play on
	 * @param currentPly the current depth
	 * @return the score of the board
	 */
	private static MiniMaxTransferObject getMin(String player, Game game, int currentPly) {
		double bestScore = Double.POSITIVE_INFINITY;
		Position indexOfBestMove = null;
		MiniMaxTransferObject miniMaxObj = new MiniMaxTransferObject();

		for (Position theMove : game.getBoard().getMovesAvailable()) {

			Game modifiedGame = getDeepCopy(game);
			modifiedGame = updateTheGame(modifiedGame, theMove);
			int score = miniMax(player, modifiedGame, currentPly).getScore();
			if (score <= bestScore) {
				bestScore = score;
				indexOfBestMove = new Position(theMove.getBoardRow(), theMove.getBoardColumn());

			}
			// logger.info("getMin Last Score: "+bestScore+", Position :"+indexOfBestMove+"
			// the Move :"+game.getBoard().getMovesAvailable());
		}
		//logger.info("[getMin]Check indexOfBestMove: " + indexOfBestMove + ", score is : " + bestScore);
		game = updateTheGame(game, indexOfBestMove);
		miniMaxObj.setGame(game);
		miniMaxObj.setScore((int) bestScore);
		miniMaxObj.setPosition(indexOfBestMove);
		logger.info("[getMin]Check Game" + game);
		return miniMaxObj;
	}

	
	/**
	 * Update the game internally for minimax checks.
	 * 
	 * @param modifiedGame
	 * @param theMove
	 * @return
	 */
	private static Game updateTheGame(Game modifiedGame, Position theMove) {

		int x = theMove.getBoardRow() - 1;
		int y = theMove.getBoardColumn() - 1;

		if (!modifiedGame.getBoard().getMovesAvailable().contains(theMove)) {
			return modifiedGame;
		}

		Board b = updateBoard(modifiedGame, theMove);
		modifiedGame.setBoard(b);
		// Check for a winner.
		if (modifiedGame.getBoard().getMovesAvailable().isEmpty()) {
			modifiedGame.setGameStatus(GameStatus.TIE);
		}
		State[][] currState = b.getCurrent_board().clone();

		modifiedGame = checkRow(x, modifiedGame, currState);
		modifiedGame = checkColumn(y, modifiedGame, currState);
		modifiedGame = checkDiagonalFromTopLeft(x, y, modifiedGame, currState);
		modifiedGame = checkDiagonalFromTopRight(x, y, modifiedGame, currState);


		Turn turn = (modifiedGame.getTurn() == Turn.FIRST_PLAYER) ? Turn.SECOND_PLAYER: Turn.FIRST_PLAYER;
		modifiedGame.setTurn(turn);

		return modifiedGame;
	}


	/**
	 * Updates the local copy of the board in memory
	 *  
	 * @param game
	 * @param theMove
	 * @return
	 */

	private static Board updateBoard(Game game, Position theMove) {
		Board b = new Board();

		int boardRow = theMove.getBoardRow() -1 ;
		int boardColumn = theMove.getBoardColumn() -1;

		State[][] old = game.getBoard().getCurrent_board();

		State[][] curr_board = new State[3][3];
		for (int i = 0; i < 3; i++) {
			curr_board[i] = old[i].clone();
		}

		ArrayList<Position> avalable = (ArrayList<Position>) game.getBoard().getMovesAvailable().stream()
				.filter(p -> !p.equals(theMove)).collect(Collectors.toList());

		// avalable.remove(theMove);

		if (curr_board[boardRow][boardColumn] == State.Blank) {
			if (game.getTurn() == Turn.FIRST_PLAYER)
				curr_board[boardRow][boardColumn ] = State.X;
			else
				curr_board[boardRow][boardColumn ] = State.O;
		}

		b.setCurrent_board(curr_board);
		b.setMovesAvailable(avalable);

		return b;
	}


	/**
	 * Creates a copy of the Game to process and identify the future. 
	 * 
	 * @param game
	 * @return
	 */
	private static Game getDeepCopy(Game game) {

		try {
			Game newGame = new Game();
			Board newBoard = new Board();

			// logger.info("Deep Copy: game: "+game);
			State[][] old = game.getBoard().getCurrent_board();

			State[][] current_board = new State[3][3];
			for (int i = 0; i < 3; i++) {
				current_board[i] = old[i].clone();
			}
			ArrayList<Position> tmp = new ArrayList<>(game.getBoard().getMovesAvailable());

			newBoard.setCurrent_board(current_board);
			newBoard.setMovesAvailable(tmp);

			newGame.setBoard(newBoard);
			newGame.setGameStatus(game.getGameStatus());
			newGame.setGameType(game.getGameType());
			newGame.setPlayer1(game.getPlayer1());
			newGame.setPlayer2(game.getPlayer2());
			newGame.setTurn(game.getTurn());
			return newGame;

		} catch (Exception e) {
			logger.info("Deep Copy: game: " + game);
			logger.info("Deep Copy: game Exception: " + e);

		}

		return game;

	}

	/**
	 * Checks the specified row to see if there is a winner.
	 * 
	 * @param row the row to check
	 * @return 
	 */
	private static Game checkRow(int row, Game game, State[][] board) {
		for (int i = 1; i < BOARD_WIDTH; i++) {
			if (board[row][i] != board[row][i - 1]) {
				break;
			}
			if (i == BOARD_WIDTH - 1) {
				game.setGameStatus(
						game.getTurn()==Turn.FIRST_PLAYER ? GameStatus.FIRST_PLAYER_WON : GameStatus.SECOND_PLAYER_WON);
			}
		}
		return game;
	}

	/**
	 * Check the specified column if there is a winner.
	 * @param column
	 * @param game
	 * @param board
	 * @return
	 */
	private static Game checkColumn(int column, Game game, State[][] board) {
		for (int i = 1; i < BOARD_WIDTH; i++) {
			if (board[i][column] != board[i - 1][column]) {
				break;
			}
			if (i == BOARD_WIDTH - 1) {
				game.setGameStatus(
						game.getTurn()==Turn.FIRST_PLAYER ?  GameStatus.FIRST_PLAYER_WON : GameStatus.SECOND_PLAYER_WON);
			}
		}
		return game;
	}

	/**
	 * Check the left diagonal to see if there is a winner.
	 * 
	 * @param x
	 * @param y
	 * @param game
	 * @param board
	 * @return
	 */
	private static Game checkDiagonalFromTopLeft(int x, int y, Game game, State[][] board) {
		if (x == y) {
			for (int i = 1; i < BOARD_WIDTH; i++) {
				if (board[i][i] != board[i - 1][i - 1]) {
					break;
				}
				if (i == BOARD_WIDTH - 1) {
					game.setGameStatus(game.getTurn()==Turn.FIRST_PLAYER ? GameStatus.FIRST_PLAYER_WON
							: GameStatus.SECOND_PLAYER_WON);
				}
			}
		}	
		return game;
	}

	/**
	 * Check the right diagonal to see if there is a winner.
	 * 
	 * @param x
	 * @param y
	 * @param game
	 * @param board
	 * @return
	 */
	private static Game checkDiagonalFromTopRight(int x, int y, Game game, State[][] board) {
		if (BOARD_WIDTH - 1 - x == y) {
			for (int i = 1; i < BOARD_WIDTH; i++) {
				if (board[BOARD_WIDTH - 1 - i][i] != board[BOARD_WIDTH - i][i - 1]) {
					break;
				}
				if (i == BOARD_WIDTH - 1) {
					game.setGameStatus(game.getTurn()==Turn.FIRST_PLAYER ?  GameStatus.FIRST_PLAYER_WON
							: GameStatus.SECOND_PLAYER_WON);
				}
			}
		}
		return game;
	}
}
