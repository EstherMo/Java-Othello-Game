import processing.core.PApplet;


public class MainApp extends PApplet {
    public static void main(String[] args){
        PApplet.main("MainApp", args);
    }

    float x = 8;
    public void settings(){
        size(1024,768);

    }
    public void setup(){


    }
    public void draw(){
        background(244, 75, 66);
        stroke(0);
        fill(127,0,0);
        ellipse(width/2,height/2,x,x);

        x++;

        if (x > width) {
            x = 0;
        }
    }


}
