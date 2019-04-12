package com.trips.ankur.nab.project.tictactoe.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

/**
 * 
 * Position class for the position object.
 * 
 * @author tripaank
 *
 */

public class Position implements Serializable{
    private int boardRow;
    private int boardColumn;

    @Override
    public String toString() {
    	return "["+boardRow+","+boardColumn+"]";
    }
}
