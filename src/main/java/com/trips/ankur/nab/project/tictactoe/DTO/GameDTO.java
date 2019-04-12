package com.trips.ankur.nab.project.tictactoe.DTO;

import com.trips.ankur.nab.project.tictactoe.enums.GameType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * GameDTO object. contains game type and the first player.
 * This is used for creation of new game.
 * 
 * @author tripaank
 *
 */
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameDTO {

    private GameType gameType;
    private String first_player;
    
}