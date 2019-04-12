package com.trips.ankur.nab.project.tictactoe.domain;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.trips.ankur.nab.project.tictactoe.enums.State;

import lombok.Getter;
import lombok.Setter;


/**
 * Represents the Tic Tac Toe board. 
 * One game contains one board.
 * 
 * @author tripaank
 * 
 */

@Getter
@Setter
@Entity
public class Board  implements Serializable {
	

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
	private Long id;
    

    @Lob
    @Column
    private State[][] current_board;
    

    @Lob
    @Column
	private ArrayList<Position> movesAvailable;
    
    
    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {

                if (current_board[y][x] == State.Blank) {
                    sb.append("-");
                } else {
                    sb.append(current_board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != 3 -1) {
                sb.append("\n");
            }
        }

        return new String(sb) + " Available :"+movesAvailable.toString() ;
    }
}