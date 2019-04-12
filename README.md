# TicTacToe - Rest Interface 
## Introduction

This API represents possible operations required for a Tic Toc Toe Game. It has been developed using Spring Boot.

## Features

* Supports a Game of **two players (player vs player)** and **one player (player vs computer)**.
* AI player uses **MiniMax Algorithm** to make moves.
* Swagger API documentation is integrated, so API can be tested using the **Swagger GUI** once the application is running using below url.
    http://localhost:8000/api/tictactoe/swagger-ui.html
* Supports more than one game at a time. User name (String) and Game ID is required to make any move.


## Future Improvements

* Authentication at the API endpoint.
* User login and player profile creation.
* Option of selecting other algorithms for AI player.
* Update in the documentation to display the API specs on the page itself.

## How to Run

* Use Maven to build the package 
* Download the zip.
* run mvn clean package.
* run the jar generated in the target folder using below command
    * java -jar target/tictactoe-0.0.1-SNAPSHOT.jar
* You can directly use the mvn plugin to run the application as well. (run in an exploded form as similar to IDE)
    * mvn spring-boot:run

## How to test/verify/play.
* Once the application is running, use swagger gui to test/verify.
* By defualt the swagger gui location will be as http://localhost:8000/api/tictactoe/swagger-ui.html
* Game can be played with a series of requests as below. 
    #### player vs player
    * Create a game using **POST** operation on **/games/game** using player name and game type as "COMPETETION".
    * Join the game using the ID of the created game and player name on **/games/join/{gameId}/{player}**
    * Place new moves using **Post** **/actions/{gameId}/{player}** and pass the move boardRow and boardColumn.
    
    ### player vs computer
    * Create a game using **POST** operation on **/games/game** using player name and game type as "COMPUTER".
    * Place new moves using **Post** **/actions/{gameId}/{player}** and pass the move boardRow and boardColumn.
    * Use **GET**  on **/actions/auto/{gameId}** to let the AI (MiniMax Algorithm)  play its move.

* API will do verification as below on every new Action.
    * Player is a part of the game.
    * Game is still runnning.
    * It is the turn of the player playing the move.
* API will do below changes once it receives a valid action.
    * Updates the Board with the action.
    * Validate if there is winner after the game.
    * Validate if the game is over.
    * Update the status of the game. 
     
        
## Known Issues

* Exceptions are not defined. Text based exceptions are being thrown from the API.
* MiniMax algorithm is still being tested and is not completly optimized. 
* Same user can have more than one open game. (Feature/Issue) 


# API Specs & Design
## Information
* This API is built in Spring Boot.
* It uses h2 database, can be changed using the configuration.
* Management Port configured as 8000, can be modified
* Base Url http://localhost:8000/api/tictactoe 

## Schema Design

_3 tables are used in the API._
- Game : Stores the information about game
- Action : Stores all the actions(moves) performed using the API.
- Board : Stores the Board information. (This is mainly used in MiniMax algorithm to find out the infromation diectly from the object)

**DB Schema is as below**
![Image of Yaktocat](https://github.com/tripsankur/TicTacToe/blob/master/doc/DB_Schema.jpeg)

## API Endpoints and Requests

Below are the operations supported by the API.

### game-controller
Game Controller supports creation of Game, Joining an existing game, getting all the list of all games, getting the list open games and some more, which are shown below in the table.

Index | Method | Path | Summary
----- | ------ | ---- | --------
1 | GET | /games | Lists all Games
2 | POST | /games/game | Creates a New Game 
3 | GET | /games/game/{id} | View the game by ID
4 | GET | /games/game/{id}/status | View the Status of the game by ID
5 | GET | /games/game/{id}/winner | Find out the winner of the game by ID
6 | POST | /games/join/{gameId}/{player} | Joins an existing Game
7 | GET | /games/{player} | Lists all Games which are waiting for Second Player

## action-controller
Action Controller helps in performing moves in the game, performing Auto Moves (AI), verifying the moves and others actions which are listed below.

Index | Method | Path | Summary
----- | ------ | ---- | -------
1 | GET | /actions/auto/{gameId} | Automatic action for computer. Computer will perform MiniMax Algorithm to identify the next move.
2 | GET | /actions/available/{gameId}/ | Returns all the available position for a game.
3 | GET | /actions/check/{gameId}/{player} | Get All actions performed in a game by the player
4 | GET | /actions/turn/{gameId}/{player} | Validates if is the turn of the player or not.
5 | GET | /actions/{gameId} | Get All actions performed in a game.
6 | POST | /actions/{gameId}/{player} | Create an action


### Full Api Spec can be found [here](https://tripsankur.github.io/TicTacToe/ApiSpec.html)

ApiSpec contains the details of the operations. This is present in the doc folder. (The link displays the renderd html)
Request/Response structure is described in the Full Spec.

### Generated Java doc's are available [here](https://tripsankur.github.io/TicTacToe/index.html)

This is the generated java doc from the project. It is available in the doc folder. (The link displayes the renderd html, which is not supported on github.)
    

#### Notes
* Some of the API operations are not requied any more after the addition of Board in the Game class it self.
* Tesing has been done using Swagger GUI.

