# Marse Rover

## Introduction

This application is written in Java 8. It performs below functions.

* It takes the input file as the argument.
* Input file should follow the below format. (From the requirement)
      
    The first line of input is the upper-right coordinates of the plateau, the lower-left coordinates are assumed to be 0,0. The rest of the input is information pertaining to the rovers that have been deployed.  Each rover has two lines of input.  The first line gives the rover's position, and the second line is a series of instructions telling the rover how to explore the plateau. The position is made up of two integers and a letter separated by spaces, corresponding to the x and y co-ordinates and the rover's orientation. Each rover will be finished sequentially, which means that the second rover won't start to move until the first one has finished moving.
      
* This application prints the final position of each of the rovers as output on the terminal.

## Assumptinos

* The size of the Board(plateau) is passed from the controller input. Default size is taken as Max Integer Val.
* If the command 'M' takes the cordinate to out of the Board(plateau), the action will not be performed.
* If the intial position of the rover is being sent as invalid coordinate (Not in the Board(plateau)), InvalidPositionException is thrown.
* Input format is defined and followed. (Exception is thrown in case the input format is not correct.)
* Excpetion InvaliActionException is thrown by the application, if the Action is different than 'L','R','M'.

## How to use the Application

This application takes 1 argument as below.

* Argument 1 - Manadatory - Complete Path of the Input File

## Future Possible Improvements 
* Program to have configuration file from where it takes the parameters.
* Program to have defined input directory, and it should poll the directory for any new input file and process the input file once it it available.


## How to Build and Run.

To Build the pacakge 

*mvn clean compile assembly:single*

To Run the application

*java -jar {target_dir}/MarseRover-0.0.1-SNAPSHOT.jar <Argument 1> 

  example : 
  java -jar C:\Users\tripaank\eclipse-workspace-jee\MarseRover\target\MarseRover-0.0.1-SNAPSHOT.jar "C:\\Users\\tripaank\\Documents\\test.txt"

## Testing 

Unit testing and Integration testing has been performed and the src is present in the application src/test/ directory.
  a. All actions validated and tested.
  b. All Exceptions validated and tested.
  c. Possible error scenarios validated and tested.

  



