# SECA PROJECT 1: OTHELLO GAME

## Technologies Used

 - Java
 - Processing

## My Process/Approach.
- Writing all the game logic in pseudocode first
 - Creating a two dimensional array to represent the 8 x 8 board game grid.
 - Using enums to set a cell to either EMPTY, BLACK or WHITE.

## Game Instructions

-  The board will start with 2 black discs and 2 white discs at the center of the board.
- The goal is to get the majority of your color discs on the board at the end of the game
- A move is made by placing a disc of the player's color on the board in a position that "out-flanks" one or more of the opponent's discs.
- Player starts off as white and switches automatically once they play a piece.
- if move is invalid, the player won't be able to play a piece.
- Alternate until the board is full or when the players decide. Whichever player has more pieces of their color on the board wins.

## Unsolved problems

- The game has a small bug where sometimes not all the pieces reverse in color, it skips one or two.
- The code checks for the array being out of bounds but sometimes the error "array index out of bound" still comes up.

## Biggest wins and challenges

 - One of the biggest wins and most challenging part to implement was the logic for a valid move. This was built by checking in every possible direction for a piece of the opposite color and only reversing the color if there is another piece of the current players color at the end.
 - It was a challenge to try to keep the code simple and easy to read. I added comments to make it easier.

### Features to implement

- show the curent valid moves for a cell when a player is hovering over it
- have a clearer pop up message when move is invalid
- show who's turn it is currently and keep score of who has more pieces on the board
- show when the game is over and who has the highest/winning score
- ability to reset game

