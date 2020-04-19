package managers;

import gameObjects.*;
import rectangles.*;
import managers.*;
import managers.GameAudio;
import managers.GameScreen;
import managers.NorthPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
public class GameManager extends JFrame implements Runnable{
    public final static int WIDTH = 330;
    public final static int HEIGHT = 700;
    public final static int NUMARROWS=6;
    public static final int NUMFOOD =9;
    private int Player_Score;
    private boolean alive;
    private boolean cheat;
    private ArrayList<Food> food;



    NorthPanel PanelNorth;
    private GameScreen gameScreen;  //The screen on which we will draw
    private GameKeyListener keyListener; //The listener for key presses
    private Player player; //The player in the dog and bone game
    private ArrayList<GameObject> gameObjects; //all game objects except the player
    private int eatenfood;
    private Thread loop;
    private Random rand;
    private boolean Shoot;

    public GameManager()
    {
        Player_Score =0;
        eatenfood =0;
        alive = true;
        cheat = false;
        this.setTitle("Jungle Adventure"); //set the title of the window
        this.setSize(WIDTH, HEIGHT); //set the width and height of the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        PanelNorth = new NorthPanel(this);
        gameScreen = new GameScreen(); //Instantiate the panel on which we will draw
        this.add(PanelNorth, BorderLayout.NORTH); // add JPanel to north of the screen
        this.add(gameScreen, BorderLayout.CENTER);  //add the JPanel to the center of the screen

        this.setVisible(true); // set the window to be visible
        keyListener = new GameKeyListener(); //instantiate the listener for key presses

        addKeyListener(keyListener); //add the key listener to the JFrame
        Shoot = false;
        rand = new Random();
        SetUpGame();

    }



    /********************************************************************
     * SetupGame: used for the initialization process for the game
     * Create the player
     * Create the game objects (gameObjects.Arrows in this case)
     */
    private void SetUpGame()
    {
        GameAudio audio = new GameAudio("Audio/TangoMusic (1).wav", true);
        //Instantiate the gameObjects.Player
        player = new Player("Images/spritesheet.png", getWindowWidth(), getWindowHeight());
        //Instantiate the game objects.  We will have 6 arrows in the game
        gameObjects = new ArrayList<GameObject>();
        food = new ArrayList<Food>();
        gameScreen.setPlayerReferenceInScreen((Player)player);
        gameScreen.setGameObjectsReferenceInScreen(gameObjects);
        gameScreen.setFoodinScreen(food);
        //Generate the ARROWS on the screen
        for (int i=0; i<NUMARROWS; i++) {
            GenerateArrows();
        }
        for (int i =0; i<NUMFOOD; i++)
        {
            GenerateFood();
        }

        RunGame();
    }

    private void GenerateFood()
    {
        food.add(new Food("Images/Food2.png"));
    }

    private void GenerateArrows()
    {
        gameObjects.add(new Arrows("Images/arrows2.png"));
    }

    public void RunGame()
    {
        gameScreen.repaint();
        repaint();
        loop = new Thread(this);
        loop.start();

    }


    @Override
    public void run() {

        while(alive) {
            for (GameObject aGameObject : gameObjects) {

                //aGameObject.setyPos((aGameObject.getyPos() + rand.nextInt(10*4)) );
                if(player.getyPos()* Player.PLAYER_SIZE > ((aGameObject.getyPos()* Arrows.SCALE)-30)
                    && (player.getyPos()* Player.PLAYER_SIZE < ((aGameObject.getyPos()*Arrows.SCALE)+30)))
                {
                    GameAudio ArrowImpact = new GameAudio("Audio/ArrowImpact.wav", false);
                    Shoot = true;
                    Arrows arrow = (Arrows) aGameObject;
                    arrow.Shoot(gameScreen, this);



                }


            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkCollisions();
            for(GameObject anArrow :gameObjects) {
                checkArrowOffScreen((Arrows)anArrow);
            }
            gameScreen.repaint();

        }
    }


    private class GameKeyListener implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(alive) {
                Player p = (Player) player;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    p.setxPos(p.getxPos() + Player.PLAYER_SPEED);
                    p.setSubImageY(2); //Want the images where character moves to the right
                    // Want the center image where he is just standing
                    if (p.getSubImageX() < 2) {
                        p.setSubImageX(p.getSubImageX() + 1);
                    } else {
                        p.setSubImageX(0);
                    }

                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    p.setxPos(p.getxPos() - Player.PLAYER_SPEED);
                    p.setSubImageY(1); //Want the images where character moves to the right
                    // Want the center image where he is just standing
                    if (p.getSubImageX() < 2) {
                        p.setSubImageX(p.getSubImageX() + 1);
                    } else {
                        p.setSubImageX(0);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    p.setyPos(p.getyPos() - Player.PLAYER_SPEED);
                    p.setSubImageY(3); //Want the images where character moves to the right
                    // Want the center image where he is just standing
                    if (p.getSubImageX() < 2) {
                        p.setSubImageX(p.getSubImageX() + 1);
                    } else {
                        p.setSubImageX(0);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    p.setyPos(p.getyPos() + Player.PLAYER_SPEED);
                    p.setSubImageY(0); //Want the images where character moves to the right
                    // Want the center image where he is just standing
                    if (p.getSubImageX() < 2) {
                        p.setSubImageX(p.getSubImageX() + 1);
                    } else {
                        p.setSubImageX(0);
                    }
                }
                p.UpdateCollisionBoxPosition();
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //Set the place to the middle picture in the first row...standing position
            Player p = (Player) player;
            if(alive)
                p.setSubImageX(1);

        }

    }

    public void checkArrowOffScreen(Arrows anArrow)
    {
        if ((anArrow.getxPos()<= 0) )
        {
            anArrow.setxPos(Arrows.FixedX);
            Shoot= false;
            System.out.println("We are not shooting anymore and bone is back to pos");
        }
    }

    public int getWindowHeight()
    {
        return this.getHeight();
    }

    public int getWindowWidth()
    {
        return this.getWidth();
    }
    public void setAlive(boolean pAlive)
    {
        alive = pAlive;
    }
    public boolean getAlive()
    {
        return alive;
    }
    public void setCheat(boolean pCheat)
    {
        cheat = pCheat;
    }
    public boolean getCheat()
    {
        return cheat;
    }


    public boolean checkCollisions()
    {
        boolean collided = false;
        if(!cheat)
        {



            for (GameObject anObject : gameObjects) {
                Arrows anArrow = (Arrows) anObject;
                if (player.getCollisionBox().intersects(anArrow.getCollisionBox())) {

                    collided = true;
                    alive = false;
                    PanelNorth.CheckButtonProperties();
                }
            }

        }
        ArrayList<Food> objectToBeRemoved = new ArrayList<Food>();
        for (Food afood : food) {

            if (player.getCollisionBox().intersects(afood.getCollisionBox())) {

                objectToBeRemoved.add(afood);
                PanelNorth.CheckButtonProperties();
                GameAudio YummySound = new GameAudio("Audio/yummy.wav", false);
                Player p = (Player) player;
                p.updatePositions();
                //p.setPlayerSize(p.PLAYER_SIZE + 0.02f);
                p.updatePositions();


            }
        }
        for (Food afood : objectToBeRemoved) {

            food.remove(afood);
            eatenfood += 1;
            Player_Score += afood.getEnergy();
            System.out.println("gameObjects.Player Score: " + Player_Score);
            System.out.println("gameObjects.Food Eaten: " + eatenfood);
        }
        gameScreen.setFoodinScreen(food);
        gameScreen.repaint();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return collided;
    }
}
