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
    boolean isValidMove;
    boolean displayInvalidMove;
    ArrayList<Boolean> validMovesArray= new ArrayList<>();

    //create hashmap of pieces to be reversed. The map will store the row and col of boxes that need to be reversed as key/value pairs
    HashMap<Integer,Integer> reverseMap = new HashMap<>();

    public void settings() {
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

            //shows invalid move error message, need to format it better
//            if (displayInvalidMove){
//                //fill(255,0,0);
//                text("invalid move", 400, 500);
//             }


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
            if(isValidMove==true){
                placePieces(row,col);
                playerWhite = !playerWhite; //switch players
            }else{
                System.out.println("this isnt a valid move");
            }
            validMovesArray.clear();
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

    // check if valid move

    void validMove(int row, int col){
        //create hashmap for sides that have a piece of the opposite color
        HashMap<String,CellState> oppositeColorDirections = new HashMap<>();


        //sets all the adjacent sides to variables if they are in array bounds
        CellState leftSide = null;
        CellState rightSide = null;
        CellState  north = null;
        CellState  south = null;
        CellState  northEast = null;
        CellState  southWest = null;
        CellState  northWest = null;
        CellState  southEast= null;

        if ((row >= 0) && (row < 8) && (col-1 >=0) && (col-1 < 8 )){
            leftSide = board[row][col -1];
        }

        if ((row >= 0) && (row < 8) && (col+1 >=0) && (col+1 < 8 )){
            rightSide = board[row][col +1];
        }

        if ((row-1 > 0) && (row-1 < 8) && (col >=0) && (col < 8 )){
            north = board[row-1][col];
        }
        if ((row+1 >= 0) && (row+1 < 8) && (col >=0) && (col < 8 )){
            south = board[row+1][col];
        }
        if ((row-1 >= 0) && (row-1 < 8) && (col+1 >=0) && (col+1 < 8 )){
            northEast= board[row-1][col+1];
        }
        if ((row+1 >= 0) && (row+1 < 8) && (col-1 >=0) && (col-1 < 8 )){
            southWest = board[row+1][col -1];
        }
        if ((row-1 >= 0) && (row-1 < 8) && (col-1 >=0) && (col-1 < 8 )){
            northWest= board[row-1][col-1];
        }
        if ((row+1 >= 0) && (row+1 < 8) && (col+1 >=0) && (col+1 < 8 )){
            southEast= board[row+1][col+1];
        }

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
                        validMovesArray.add(true);
                        break;
                    } else if(board[row][col-leftCount]==CellState.EMPTY){
                        System.out.println(reverseMap);
                        reverseMap.clear();  //clear reverseMap if cell is empty
                        validMovesArray.add(false);
                        displayInvalidMove = true;
                        break;
                    }
                    System.out.println("leftside" +reverseMap);
                }

            } else if(key =="rightSide"){
                int rightCount = 1;
                while(col+rightCount <=7 || board[row][col+rightCount]==CellState.EMPTY ){
                    if(board[row][col+rightCount]==oppositeColor){
                        reverseMap.put(row,col+rightCount);
                        rightCount ++;
                    } else if (board[row][col+rightCount]==playersColor){
                        reversePieces();
                        validMovesArray.add(true);
                        break;
                    } else if(board[row][col+rightCount]==CellState.EMPTY){
                        System.out.println(reverseMap);
                        reverseMap.clear();
                        validMovesArray.add(false);
                        break;
                    }
                    System.out.println("rightside" + reverseMap);
                }

            } else if (key == "north"){
                int northCount = 1;
                while(row-northCount >=0 || board[row-northCount][col]==CellState.EMPTY ){
                    if(board[row-northCount][col]==oppositeColor){
                        reverseMap.put(row-northCount,col);
                        northCount ++;
                    } else if (board[row-northCount][col]==playersColor){
                        reversePieces();
                        validMovesArray.add(true);
                        break;
                    } else if (board[row-northCount][col]==CellState.EMPTY){
                        reverseMap.clear();
                        validMovesArray.add(false);
                        break;
                    }
                    System.out.println("North" + reverseMap);
                }

            } else if (key == "south"){
                int southCount = 1;
                while(row+southCount <=7|| board[row+southCount][col]==CellState.EMPTY ){
                    if(board[row+southCount][col]==oppositeColor){
                        reverseMap.put(row+southCount,col);
                        southCount ++;
                    } else if (board[row+southCount][col]==playersColor){
                        reversePieces();
                        validMovesArray.add(true);
                        break;
                    } else if (board[row+southCount][col]==CellState.EMPTY){
                        reverseMap.clear();
                        validMovesArray.add(false);
                        break;
                    }
                    System.out.println("south" +reverseMap);
                }

            } else if(key == "northEast"){
                int NERowCount = 1;
                int NEColCount = 1;
                while(row - NERowCount >= 0|| col + NEColCount <= 7 || board[row - NERowCount][col + NEColCount]==CellState.EMPTY ){
                    if(board[row - NERowCount][col + NEColCount]==oppositeColor){
                        reverseMap.put(row - NERowCount,col + NEColCount);
                        NERowCount ++;
                        NEColCount ++;
                    } else if (board[row - NERowCount][col + NEColCount]==playersColor){
                        reversePieces();
                        validMovesArray.add(true);
                        break;
                    } else if (board[row - NERowCount][col + NEColCount]==CellState.EMPTY){
                        reverseMap.clear();
                        validMovesArray.add(false);
                        break;
                    }
                    System.out.println("south" +reverseMap);
                }
            }else if(key == "northWest"){
                int NWRowCount = 1;
                int NWColCount = 1;
                while(row - NWRowCount >= 0|| col - NWColCount >= 0 || board[row - NWRowCount][col - NWColCount]==CellState.EMPTY ){
                    if(board[row - NWRowCount][col - NWColCount]==oppositeColor){
                        reverseMap.put(row - NWRowCount,col - NWColCount);
                        NWRowCount ++;
                        NWColCount ++;
                    } else if (board[row - NWRowCount][col - NWColCount] == playersColor){
                        reversePieces();
                        validMovesArray.add(true);
                        break;
                    } else if (board[row - NWRowCount][col - NWColCount] == CellState.EMPTY){
                        reverseMap.clear();
                        validMovesArray.add(false);
                        break;
                    }
                    System.out.println("south" +reverseMap);
                }

            } else if (key =="southWest"){
                int SWRowCount = 1;
                int SWColCount = 1;
                while(row + SWRowCount <= 7|| col - SWColCount >= 0 || board[row + SWRowCount][col - SWColCount]==CellState.EMPTY ){
                    if(board[row + SWRowCount][col - SWColCount]==oppositeColor){
                        reverseMap.put(row + SWRowCount,col - SWColCount);
                        SWRowCount ++;
                        SWColCount ++;
                    } else if (board[row + SWRowCount][col - SWColCount] == playersColor){
                        reversePieces();
                        validMovesArray.add(true);
                        break;
                    } else if (board[row + SWRowCount][col - SWColCount] == CellState.EMPTY){
                        reverseMap.clear();
                        validMovesArray.add(false);
                        break;
                    }
                    System.out.println("south" +reverseMap);
                }

            }else if (key =="southEast"){
                int SERowCount = 1;
                int SEColCount = 1;
                while(row + SERowCount <= 7|| col + SEColCount <= 7|| board[row + SERowCount][col + SEColCount]==CellState.EMPTY ){
                    if(board[row + SERowCount][col + SEColCount]==oppositeColor){
                        reverseMap.put(row + SERowCount,col + SEColCount);
                        SERowCount ++;
                        SEColCount ++;
                    } else if (board[row + SERowCount][col + SEColCount] == playersColor){
                        reversePieces();
                        validMovesArray.add(true);
                        break;
                    } else if (board[row + SERowCount][col + SEColCount] == CellState.EMPTY){
                        reverseMap.clear();
                        validMovesArray.add(false);
                        break;
                    }
                    System.out.println("south" +reverseMap);
                }
            }else{

            }

            //check if there are any valid moves in this arraylist, set validmoves to true if yes
            if (validMovesArray.contains(true)){
                isValidMove = true;
            } else {
                isValidMove= false;
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


//TO DO:
//show the valid moves, if cell is a valid move, fill it with highlighted color.
//pop up message when move is invalid.
//show who's turn it's currently and score keeping
//Show when the game is over and who has the highest score


