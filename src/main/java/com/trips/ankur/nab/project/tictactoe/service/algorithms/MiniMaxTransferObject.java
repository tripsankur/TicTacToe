package com.trips.ankur.nab.project.tictactoe.service.algorithms;

import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.domain.Position;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Used for MiniMax algorithm as helper objects for AI player.
 * 
 * @author tripaank
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@ToString
@EqualsAndHashCode
public class MiniMaxTransferObject {
	Game game;
	Position position;
	int score;

}
