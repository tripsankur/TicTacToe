package com.trips.ankur.nab.project.tictactoe.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.trips.ankur.nab.project.tictactoe.domain.Board;


/**
 * BoardRepository to store the Board in DB.
 * 
 * @author tripaank
 *
 */

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {

	
}