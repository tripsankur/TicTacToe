package com.trips.ankur.nab.project.tictactoe.DTO;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Data Transfer Object for New Moves.
 * @author tripaank
 *	
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateActionDTO {
    @NotNull
    @Max(value=3)
    @Min(value=1)
    int boardRow;
    
    @NotNull 
    @Max(value=3)
    @Min(value=1)
    int boardColumn;

    
}
