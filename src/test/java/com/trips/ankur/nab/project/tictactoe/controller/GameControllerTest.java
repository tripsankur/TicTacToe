package com.trips.ankur.nab.project.tictactoe.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.trips.ankur.nab.project.tictactoe.DTO.GameDTO;

import com.trips.ankur.nab.project.tictactoe.domain.Game;
import com.trips.ankur.nab.project.tictactoe.enums.GameStatus;
import com.trips.ankur.nab.project.tictactoe.enums.GameType;

import com.trips.ankur.nab.project.tictactoe.service.GameService;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GameControllerTest {

	@Autowired
	MockMvc mockMvc;

	
	 @Autowired 
	 protected WebApplicationContext wac;
	 	
	@MockBean
	GameService gameService;

	
	
	@Autowired
	GameController gameController;
	
	
	GameDTO gameComptetion;
	
	
	
	@Before
	public void setUp() throws Exception {
		gameComptetion = GameDTO.builder().first_player("Ankur").gameType(GameType.COMPETITION).build();
	}
	
	/*
	 * //Example Test
	 * 
	 * @Test public void exampleTest() throws Exception {
	 * this.mockMvc.perform(get("/games/hello")).andExpect(status().isOk())
	 * .andExpect(content().string("Hello")); }
	 */

	@After
	public void tearDown() throws Exception {
	}
	

	@Test
	public void testGetAllGames() {
		
	}

	@Test
	public void testGetAllGamesToJoin() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddGame() throws Exception {
		// RequestBuilder rq =
		
		  when(gameService.createNewGame(any(GameDTO.class)))
		  .thenReturn(Game.builder().id(1L).player1("Ankur")
		  .gameType(GameType.COMPETITION).gameStatus(GameStatus.WAITS_FOR_PLAYER).
		  created(new Date()).build ());
		 
		 
		// mockMvc.perform(requestBuilder)
		Gson gson = new Gson();

		  RequestBuilder requestBuilder =
		  MockMvcRequestBuilders.post("/games/game")
		  .content(gson.toJson((gameComptetion))).contentType(MediaType.
		  APPLICATION_JSON); 
		  MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
		  MockHttpServletResponse response = result.getResponse();
		  assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void testJoinGame() throws Exception {
		  when(gameService.createNewGame(any(GameDTO.class)))
		  .thenReturn(Game.builder().id(1L).player1("Ankur")
		  .gameType(GameType.COMPETITION).gameStatus(GameStatus.WAITS_FOR_PLAYER).
		  created(new Date()).build ());
		 
		 
		// mockMvc.perform(requestBuilder)
		Gson gson = new Gson();

		  RequestBuilder requestBuilder =
		  MockMvcRequestBuilders.post("/games/game")
		  .content(gson.toJson((gameComptetion))).contentType(MediaType.
		  APPLICATION_JSON); 
		  MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
		  MockHttpServletResponse response = result.getResponse();
		  assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}

	@Test
	public void testGetGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGameStatus() {
		fail("Not yet implemented");
	}

}
