package com.trips.ankur.nab.project.tictactoe.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trips.ankur.nab.project.tictactoe.domain.Board;
import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.domain.Position;
import com.trips.ankur.nab.project.tictactoe.enums.State;
import com.trips.ankur.nab.project.tictactoe.enums.Turn;
import com.trips.ankur.nab.project.tictactoe.repository.BoardRepository;
import com.trips.ankur.nab.project.tictactoe.repository.GameRepository;
import com.trips.ankur.nab.project.tictactoe.service.algorithms.GameLogic;


/**
 * Board Service class
 * 
 * @author tripaank
 *
 */

@Service
@Transactional
public class BoardService {
	static final int BOARD_WIDTH = 3;
	private final BoardRepository boardRepository;
	Logger logger = LoggerFactory.getLogger(GameService.class);

	@Autowired
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}


	/**
	 * Creating a new board.
	 * 
	 * @return new Board
	 */
	public Board createNewBoard() {
		Board board=new Board();

		State[][] current_Board= new State[BOARD_WIDTH][BOARD_WIDTH];
		for (int row = 0; row < BOARD_WIDTH; row++) {
			for (int col = 0; col < BOARD_WIDTH; col++) {
				current_Board[row][col] = State.Blank;
			}
		}
		board.setCurrent_board(current_Board);
		board.setMovesAvailable(GameLogic.getAllPositions());
		boardRepository.save(board);

		return board;
	}


	/**
	 * Updating a board after a move.
	 * 
	 * @param boardRow
	 * @param boardColumn
	 * @param game
	 * @param player
	 * @param position
	 * @return the board.
	 */
	public Board updateBoard(int boardRow, int boardColumn, Game game, String player,List<Position> position) {

		Board b = GameLogic.updateBoard(boardRow, boardColumn, game, player, position);
		boardRepository.save(b);
		return b;
	}









}
