package com.trips.ankur.nab.project.tictactoe.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.trips.ankur.nab.project.tictactoe.repository.ActionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionServiceTest {

	@Autowired
	private ActionService actionService;
	
	@MockBean
	private ActionRepository actionRepository;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testActionService() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateAction() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateAutoAction() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckCurrentGameStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActionsInGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPlayerActionPositionsInGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTheNumberOfPlayerActionsInGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsPlayerTurn() {
		fail("Not yet implemented");
	}

}
