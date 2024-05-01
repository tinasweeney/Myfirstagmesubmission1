
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

//*******************************************************************************
// Class Definition Section

public class GameLand implements Runnable, KeyListener {
    //Variable Declaration Section
    //Declare the variables used in the program
    //You can set their initial values here if you want

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    //declare screen/level booleans
    public boolean gameOver=false;
    public int counter=0;

    public int score;

    //declare time variables
    public long startTime;
    public long currentTime;
    public long elapsedTime;

    //Declare the objects used in the program below
    /***Step 0 declare object ***/
   public Hero Frog;
   public Hero Fly ;

    /*** Step 1 declare image for object ***/
   public Image flyPic;
   public Image frogPic;


    //declare background image
    public Image backgroundPic;
    public Image gameOverPic;

    public Fly[] bugs;

    //intersection booleans
    //public boolean astroIsIntersectingButterfly = false;
    //public boolean dogIsIntersectingButterfly = false;

    //three steps to add an image
    //1. declare, 2. construct, 3. use (you fo this for the object and do it again for the image) ~ only adding image; like background then you only used first

    public static void main(String[] args) {
        GameLand ex = new GameLand();   //creates a new instance of the game and tells GameLand() method to run
        new Thread(ex).start();       //creates a thread & starts up the code in the run( ) method
    }

    // Constructor Method
    public GameLand() {
        setUpGraphics();
        /*** Step 2 construct object ***/
     Fly = new Hero(200,300,0,4,60,80);
       Frog = new Hero(800,600,1000,1000,150,150);

        /***Step 3 add image to object ***/
       // astroPic=Toolkit.getDefaultToolkit().getImage("astronaut.png");
        flyPic= Toolkit.getDefaultToolkit().getImage("fly.png");
        frogPic=Toolkit.getDefaultToolkit().getImage("Frog.png");
        backgroundPic=Toolkit.getDefaultToolkit().getImage("Background Pond.jpg");
        gameOverPic=Toolkit.getDefaultToolkit().getImage("gameOverPic.png");

        /**Step 2 for Arrays: Construct Array and give a size**/
        bugs = new Fly[100];
        /**Step 3 for Arrays: Fill the array with variables or objects**/
        for(int i=0; i< bugs.length; i++) {
            int randomX = (int)(Math.random()*1000);
            int randomY = -(i*300);//change this number
            bugs[i]=new Fly (randomX, randomY, 0, 5,150,150); //include other variables

        }
        startTime=System.currentTimeMillis();

        Fly.printInfo();
        Frog.printInfo();

    }// GameLand()

//*******************************************************************************
//User Method Section
    // this is the code that plays the game after you set things up
    public void run() {
        while (true) {
            timer();
            moveThings();  //move all the game objects
            collisions();
            render();  // paint the graphics
            pause(20); // sleep for 20 ms
        }
    }
    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw our background Image below:
        g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
        g.drawString("score: "+ score,100,60);
        g.drawString("time:" + elapsedTime,900, 60);

        //draw the image of your objects below:
        /** Step 4 draw  object images ***/
        for(int i=0; i< bugs.length; i++) {
            g.drawImage(flyPic, bugs[i].xpos, bugs[i].ypos, bugs[i].width, bugs[i].height, null);
        }
        if (Frog.isAlive == true) {
            g.drawImage(frogPic, Frog.xpos, Frog.ypos, Frog.width, Frog.height, null);
            g.drawImage(flyPic, Fly.xpos, Fly.ypos, Fly.width, Fly.height, null);

        if (gameOver==true){
            //paint game over image to the screen
            g.drawImage(gameOverPic,0,0, WIDTH,HEIGHT,null);
        }

            g.dispose();
            bufferStrategy.show();
        }
    }
    public void timer(){
        //get the current time
        currentTime = System.currentTimeMillis();
        //calculate the elapsed time, convert it to seconds and cast as int
        elapsedTime=30-((int)((currentTime-startTime)*.001));
        if(elapsedTime==0){
            gameOver=true;

        }
    }
    public void moveThings() {
        //call the move() method code from your object class
        Fly.move();
        Frog.userMove();

        for(int i=0; i < bugs.length; i++){
            bugs[i].wrappingMove();
        }
    }


    public void collisions() {

        for(int i=0; i < bugs.length; i++){
            if (bugs[i].rec.intersects(Frog.rec) && bugs[i].isAlive==true && bugs[i].isIntersecting==false){
                // the outcome of the collision goes here
                bugs[i].isIntersecting=true;
                System.out.println("score: "+ score);
                score=score+1;
            }
            if (bugs[i].rec.intersects(Frog.rec)==false){
                bugs[i].isIntersecting=false;
            }
        }

          //  if (astro.rec.intersects(butterfly.rec) && astroIsIntersectingButterfly) {
               // butterfly.dx = 6;
            //}
        }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }
    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Game Land");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);
        canvas.addKeyListener(this);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

    @Override
    public void keyPressed(KeyEvent e){
        char key= e.getKeyChar();
        int keyCode=e.getKeyCode();
        System.out.println("Key: "+ key+ ", KeyCode: " + keyCode);
        if(keyCode==68){//d is 68 // right movement
            Frog.rightPressed=true;
        }
        if (keyCode ==65) {//a is a 65
            Frog.leftPressed = true;
        }
        if (keyCode==87){
            Frog.upPressed=true;
        }
        if (keyCode==83) {//s is 83
            Frog.downPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        char key =e.getKeyChar();
        int keyCode=e.getKeyCode();
        if (keyCode == 68) {
            Frog.rightPressed=false;
        }
        if (keyCode == 65) {
            Frog.leftPressed=false;
        }
        if (keyCode==87){
            Frog.upPressed=false;
        }
        if (keyCode==83){
            Frog.downPressed=false;
        }

    }

    @Override
    public void keyTyped(KeyEvent e){

    }
}
