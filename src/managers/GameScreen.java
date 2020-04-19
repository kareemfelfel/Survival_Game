package managers;

import gameObjects.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
public class GameScreen extends JPanel {

    private GameObject playerReference;
    private ArrayList<GameObject> gameObjReference;
    private ArrayList<Food> foodref;
    private Image backgroundIMG;
    //Constructor -- will decide what goes in here shortly
    public GameScreen()
    {
        backgroundIMG = Toolkit.getDefaultToolkit().createImage("Images/Pixel_Grass.png");
    }

    //We need to be able to
    public void setPlayerReferenceInScreen(GameObject pPlayer)
    {

        playerReference = pPlayer;
    }

    //We need to be able to
    public void setGameObjectsReferenceInScreen(ArrayList<GameObject> pGameObjs)
    {

        gameObjReference= pGameObjs;
    }
    public void setFoodinScreen(ArrayList<Food> pfood)
    {
        foodref = pfood;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundIMG, 0, 0, null);
        //Tell the player to draw herself

        //Tell game objects (bones) to draw themselves
        if (gameObjReference != null)
        {
            for(GameObject aGameObject : gameObjReference)
            {
              aGameObject.draw(g);
            }
        }
        if (playerReference != null) {
            playerReference.draw(g);
        }
        if(foodref!= null)
        {
            for(Food afood : foodref)
            {
                if(afood != null) {
                    afood.draw(g);
                }
            }
        }

    }
}
