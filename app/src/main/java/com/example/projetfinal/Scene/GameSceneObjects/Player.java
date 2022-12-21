package com.example.projetfinal.Scene.GameSceneObjects;


import android.content.Context;
import android.graphics.Canvas;

import com.example.projetfinal.DrawingUtilityClass.CustomImage;
import com.example.projetfinal.Scene.Scene;
import com.example.projetfinal.Utilities.Vector;

/**
 * Player functionalities
 */
public class Player {
    private float positionX;
    private float positionY;

    private final float size = 35;
    private final float speed = 4;

    private static CustomImage skin;


    /**
     * Constructor of Player that sets the initial wanted parameters
     * @param context
     * Reference to the Activity Context
     * @param x
     * Position X
     * @param y
     * Position Y
     */
    public Player(CustomImage skin, Context context, float x, float y)
    {
        this.skin = skin;
        positionX =x;
        positionY=y;
    }

    /**
     * Draw the player at his position
     * @param canvas
     * Reference to the canvas
     */
    public void draw(Canvas canvas)
    {
        skin.draw(canvas);
    }



    /**
     * Update the player's position and the collision update
     * @param playerInput
     * Input of the user pressing on the screen
     * @param deltatime
     * Elapsed time
     */
    public void update(Vector playerInput, double deltatime)
    {
        //Change position based on deltaTime to prevent shifts with possible inconsistent framerate
        deltatime /= 1000;
        positionX += playerInput.getX() * speed * deltatime;
        positionY += playerInput.getY() * speed * deltatime;


        skin.setPosition(positionX,positionY);
        skin.update();
        collisionUpdate();
    }






    /**
     * Set the player's position
     * @param x
     * Position X
     * @param y
     * Position Y
     */
    public void setPosition(float x, float y)
    {
        positionX=x;
        positionY=y;
    }

    /**
     * Get the player's position
     * @return
     * Vector of the position of the player
     */
    public Vector getPosition()
    {
        return new Vector(positionX,positionY);
    }

    /**
     * Get the player's size
     * @return
     * Radius of the player
     */
    public float getSize()
    {
        return size;
    }




    private void collisionUpdate()
    {
        if(positionX > Scene.getCanvas().getX())
        {
            positionX = Scene.getCanvas().getX();
        }
        else if(positionX < 0){positionX = 0;}

        if(positionY > Scene.getCanvas().getY())
        {
            positionY = Scene.getCanvas().getY();
        }
        else if(positionY < 0){positionY = 0;}

    }


}

