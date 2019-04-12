package com.trips.ankur.nab.project.tictactoe.DTO;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.trips.ankur.nab.project.tictactoe.enums.GameStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author tripaank ActionD for transfer of Action Object
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO {

    @NotNull
	@Max(value = 3)
	@Min(value = 1)
	private int boardRow;
    
    @NotNull
	@Max(value = 3)
	@Min(value = 1)
	private int boardColumn;

	private Date created;
	private String userName;
	private GameStatus gameStatus;



}
