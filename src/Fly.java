import java.awt.*;

public class Fly {

    //variable declaration

    public int xpos;      //the x position
    public int ypos;      // the y position
    public int dx;        //the speed of the hero in the x direction
    public int dy;        //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;    //a boolean to denote if the hero is alive or not
    public Rectangle rec;
    public Image pic;
    //movement booleans
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean upPressed;
    public boolean downPressed;

    public boolean isIntersecting;

    //constructor method
    public Fly(int pXpos, int pYpos, int pDx, int pDy, int pWidth, int pHeight) {
        xpos = pXpos;
        ypos = pYpos;
        dx = pDx;
        dy = pDy;
        width = pWidth;
        height = pHeight;
        isAlive = true;
        rec=new Rectangle (xpos,ypos,width,height);

    }

    public void printInfo() {
        System.out.println("X position: " + xpos);
        System.out.println("Y position: " + ypos);
        System.out.println("x speed:" + dx);
        System.out.println("y speed:" + dy);
        System.out.println("Width:" + width);
        System.out.println("Height:" + height);
        System.out.println("Is your Hero alive? " + isAlive);
    }

    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec=new Rectangle(xpos,ypos,width,height);
    }

    public void bouncingMove(){
        if (xpos>1000){
            dx=-dx;
        }
        if (xpos <0){
            dx=-dx;
        }
        if (ypos < 1 ){
            ypos = 700;
        }
        if (ypos>700){
            ypos=1;
        }

        xpos = xpos + dx;
        ypos = ypos + dy;
        rec=new Rectangle(xpos,ypos,width,height);
    }


    public void wrappingMove() {
        //as an astronaut hits a wall, it shows up on the opposite wall.
        if (xpos > 1000) {
            xpos = 1;
        }
        if (xpos < 0) {
            xpos = 1000;
        }

//        if (ypos < 1) {
//            ypos = 700;
//        }

        if (ypos>700){
            ypos=-10000;
        }

        //the two lines below actually tell your characters to move
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec=new Rectangle(xpos,ypos,width,height);
    }

}

//object class called hero needs:
//declaration section variables
//constructor
//print Info


