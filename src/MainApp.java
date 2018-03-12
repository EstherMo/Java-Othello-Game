import javafx.scene.control.Cell;
import processing.core.PApplet;
import java.util.ArrayList;
import java.util.*;

import java.lang.reflect.Array;


public class MainApp extends PApplet {
    public static void main(String[] args) {
        PApplet.main("MainApp", args);
    }

    int rows, columns;
    CellState[][] board;
    int colwidth, rowheight;
    boolean playerWhite = true;
    CellState  oppositeColor;
    CellState playersColor;
    boolean validMove;

    //create hashmap of pieces to be reversed. The map will store the row and col of boxes that need to be reversed as key/value pairs
    HashMap<Integer,Integer> reverseMap = new HashMap<>();

    public void settings() {
        //size(1024,768);
        size(600, 600);
        smooth();
        rows = 8;
        columns = 8;

        colwidth = width / columns;
        rowheight = height / rows;

        //multidimensional array to represent the grid squares 8 x 8

        board = new CellState[rows][columns];

        //Set all cells to empty
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board[x][y] = CellState.EMPTY;
            }
        }


        //Setup the 4 starting pieces

        board[4][3] = CellState.BLACK;
        board[3][4] = CellState.BLACK;
        board[3][3] = CellState.WHITE;
        board[4][4] = CellState.WHITE;

        //click on square

//        int row = mouseY / (rowheight);
//        int col = mouseX / (colwidth);

//        if (board[row][col] == CellState.BLACK) {
//
//        }


    }

    public void setup() {


    }

    public void draw() {
        {
            //green background
            background(0, 155, 30);
            stroke(0);
            strokeWeight(4);


            //cursor point piece

            ellipse(mouseX, mouseY, 50, 50);


            drawGrid();
            drawPieces();





        }
    }

    //creates the grid lines

    void drawGrid(){
        for (int i = 0; i < rows; i++) {
            line(0, i * height / rows, width, i * height / rows);
        }
        for (int j = 0; j < columns; j++) {
            line(j * width / columns, 0, j * width / columns, height);
        }
    }

    //place piece when mouse is released

    public void mouseReleased() {
        int row = mouseY / (rowheight);
        int col = mouseX / (colwidth);

        //if cell is empty, check if its a valid move
        if(board[row][col] == CellState.EMPTY){
            reverseMap.clear();
            validMove(row,col);
            if(validMove==true){
                placePieces(row,col);
                playerWhite = !playerWhite; //switch players
            }else{
                System.out.println("this isnt a valid move");
            }
        }
    }

    //place piece

    void placePieces(int row,int col){
        if (playerWhite) { //if current player is white place a white piece
            board [row][col] = CellState.WHITE;
            //playerWhite = !playerWhite;
        } else if (!playerWhite){   //if current player is black place a black piece
            board [row][col] = CellState.BLACK;
            //playerWhite = !playerWhite;
        }
    }


    //draw play pieces

    void drawPieces(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == CellState.WHITE) {
                    fill(255);
                    ellipse((j + 0.5f) * colwidth, (i + 0.5f) * rowheight, colwidth * 0.9f, rowheight * 0.9f);
                } else if (board[i][j] == CellState.BLACK) {
                    fill(0);
                    ellipse((j + 0.5f) * colwidth, (i + 0.5f) * rowheight, colwidth * 0.9f, rowheight * 0.9f);
                }
            }
        }
    }

    //show the valid moves

    //if box is valid move, fill it with highlighted color


    //show whos turn

    // check if valid move

    void validMove(int row, int col){
        //create hashmap for sides that have a piece of the opposite color
        HashMap<String,CellState> oppositeColorDirections = new HashMap<>();


        //set all the adjacent sides to variables

        CellState  leftSide = board[row][col -1];
        CellState  rightSide = board[row][col +1];
        CellState  north = board[row-1][col];
        CellState  south = board[row+1][col];
        CellState  northEast= board[row-1][col+1];
        CellState  southWest = board[row+1][col -1];
        CellState  northWest= board[row-1][col-1];
        CellState  southEast= board[row+1][col+1];



        // hashmap of all the adjacent cells
        HashMap<String,CellState> directionsMap = new HashMap<>();
        directionsMap.put("leftSide", leftSide);
        directionsMap.put("rightSide", rightSide);
        directionsMap.put("north", north);
        directionsMap.put("south", south);
        directionsMap.put("northEast", northEast);
        directionsMap.put("northWest", northWest);
        directionsMap.put("southWest", southWest);
        directionsMap.put("southEast", southEast);

        //check if adjacent cells are of the opposite color, then add them to the directionsArray

        for (String key : directionsMap.keySet()) {
            if(playerWhite){
                if (directionsMap.get(key)== CellState.BLACK){
                    oppositeColorDirections.put(key,directionsMap.get(key));
                    oppositeColor = CellState.BLACK;
                    playersColor = CellState.WHITE;
                }
            } else {
                if (directionsMap.get(key)== CellState.WHITE){
                    oppositeColorDirections.put(key,directionsMap.get(key));
                    oppositeColor = CellState.WHITE;
                    playersColor = CellState.BLACK;
                }
            }
        }



        //if one side has an opposite color cell adjacent to it, continue checking in that direction

        for (String key : oppositeColorDirections.keySet()) {
            if(key =="leftSide"){
                System.out.println(oppositeColorDirections);
                int leftCount = 1;
                while(col-leftCount >=0 || board[row][col-leftCount]==CellState.EMPTY ){
                    if(board[row][col-leftCount]==oppositeColor){ // if nearby piece is also black
                        reverseMap.put(row,col-leftCount);
                        leftCount ++;
                    } else if (board[row][col-leftCount]==playersColor){
                        reversePieces();
                        validMove = true;
                        break;
                    } else {
                        validMove = false;
                    }
                    System.out.println(reverseMap);
                }

//            } else if(key =="rightSide"){
//                int rightCount = 1;
//                while(col+rightCount <=7 || board[row][col+rightCount]==CellState.EMPTY ){
//                    if(board[row][col+rightCount]==oppositeColor){ // if nearby piece is also black
//                        reverseMap.put(row,col+rightCount);
//                        rightCount ++;
//                    } else if (board[row][col+rightCount]==playersColor){
//                        reversePieces();
//                        validMove = true;
//                        break;
//                    } else {
//                        validMove = false;
//                    }
//                    System.out.println(reverseMap);
//                }

            } else if (key == "north"){

            } else if (key == "south"){

            } else if(key == "northEast"){

            }else if(key == "northWest"){

            } else if (key =="southWest"){

            }else if (key =="southEast"){

            }else{

            }

        }

    }



    //reverse the colors of all pieces in between that are of the opposite color
    void reversePieces(){
        for (Integer key : reverseMap.keySet()) {
            if (playerWhite){
                board[key][reverseMap.get(key)] = CellState.WHITE;
            } else {
                board[key][reverseMap.get(key)] = CellState.BLACK;
            }
        }
    }


}
