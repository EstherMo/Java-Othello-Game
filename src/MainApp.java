import processing.core.PApplet;


public class MainApp extends PApplet {
    public static void main(String[] args){
        PApplet.main("MainApp", args);
    }

    int rows, columns;
    int colwidth, rowheight;

    public void settings(){
        //size(1024,768);
        size( 600, 600);
        smooth();
        rows = 8;
        columns = 8;

        colwidth = width/columns;
        rowheight = height/rows;

        //multidimensional array to represent the grid squares

        CellState[][] board = new CellState[rows][columns];

        //Setup the 4 starting pieces

        board[4][3] = CellState.BLACK;
        board[3][4] = CellState.BLACK;
        board[3][3] = CellState.WHITE;
        board[4][4] = CellState.WHITE;


    }
    public void setup(){


    }
    public void draw(){
        {
            //green background
            background(0, 155, 30);
            stroke(0);
            strokeWeight(4);

            //creates the grid lines

            for (int i=0; i<rows; i++)
            {
                line (0, i*height/rows, width, i*height/rows);
            }
            for (int j=0; j<columns; j++)
            {
                line (j*width/columns, 0, j*width/columns, height);
            }
        }
    }


}
