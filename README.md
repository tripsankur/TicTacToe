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



## Known Issues



# API Specs & Design Descriptions
### Information
* This API is built in Spring Boot.
* It uses h2 text based database. 
* Management Port configured as 8000, can be modified
* Base Url http://localhost:8000/api/tictactoe 

### Schema Design



## API Endpoints and Requests
### game-controller
Game Controller

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
Action Controller

Index | Method | Path | Summary
----- | ------ | ---- | -------
1 | GET | /actions/auto/{gameId} | Automatic action for computer
2 | GET | /actions/available/{gameId}/ | Returns all the available position for a game.
3 | GET | /actions/check/{gameId}/{player} | Get All actions performed in a game by the player
4 | GET | /actions/turn/{gameId}/{player} | Validates if is the turn of the player or not.
5 | GET | /actions/{gameId} | Get All actions performed in a game.
6 | POST | /actions/{gameId}/{player} | Create an action




### Full Api Spec can be found [here](http://htmlpreview.github.io/?https://github.com/tripsankur/TicTacToe/blob/master/doc/ApiSpec.html)

ApiSpec contains the details of the operations. This is present in the doc folder. (The link displays the renderd html)

### Generated Java doc's are available [here](http://htmlpreview.github.io/https://github.com/tripsankur/TicTacToe/blob/master/doc/index.html)

This is the generated java doc from the project. It is available in the doc folder. (The link displayes the renderd html, which is not supported on github.)
    

    
