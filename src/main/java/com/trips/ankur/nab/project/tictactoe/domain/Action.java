package com.trips.ankur.nab.project.tictactoe.domain;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


/**
 * Action class. 
 * Stores the action in DB.
 * 
 * @author tripaank
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Max(value=3)
    @Min(value=1)
    @Column(name = "board_row", nullable = false)
    private int boardRow;

    @Max(value=3)
    @Min(value=1)
    @Column(name = "board_column", nullable = false)
    private int boardColumn;
    
    @Column(name = "player_name", nullable = true)
    private String player;

    @Column(name = "created", nullable = false)
    private Date created;
    
}
