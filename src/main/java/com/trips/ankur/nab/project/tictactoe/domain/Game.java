package com.trips.ankur.nab.project.tictactoe.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Check;


import com.trips.ankur.nab.project.tictactoe.enums.GameStatus;
import com.trips.ankur.nab.project.tictactoe.enums.GameType;
import com.trips.ankur.nab.project.tictactoe.enums.Turn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Game class for storing the game in DB.
 * 
 * @author tripaank
 *
 */

@Entity
@Check(constraints = "game_type = 'COMPUTER' or game_type = 'COMPETITION' " +
		"and game_status = 'IN_PROGRESS' or game_status = 'FIRST_PLAYER_WON' or game_status = 'SECOND_PLAYER_WON'" +
		"or game_status = 'TIE' or game_status = 'WAITS_FOR_PLAYER' ")
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
	/*
	 * ID
	 * Status
	 * Players
	 * Creation Time
	 * Board Status
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "first_player", nullable = false)
	private String player1;

	@Column(name = "second_player", nullable = true)
	private String player2;



	@Enumerated(EnumType.STRING)
	private GameStatus gameStatus;

	@Enumerated(EnumType.STRING)
	private GameType gameType;

	@Enumerated(EnumType.STRING)
	private Turn turn;	

	@Column(name = "date_created", nullable = false)
	private Date created;

	@OneToOne
	@JoinColumn(name = "board_id", nullable = false) 
	private Board board;


}
