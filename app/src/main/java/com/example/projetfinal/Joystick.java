package com.example.projetfinal;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

/**
 * Joystick class that encapsulate all of the Joystick functionalities
 */
public class Joystick {
    private Vector position;
    private Vector relativePosition;
    private Vector playerInput;


    private final float baseRadius = 80;
    private final float joystickRadius = 40;

    private final Paint paint1;
    private final Paint paint2;


    /**
     * Constructor that initialize all of the Joystick base settings
     * @param context
     */
    public Joystick(Context context)
    {

        //Initialize the joystick Start position to outside the screen
        playerInput = new Vector(0,0);
        position = new Vector(-50,-50);
        relativePosition= new Vector(-500,-500);

        //Creates the joystick paint reference
        paint1 = new Paint();
        paint2 = new Paint();
        paint1.setColor(ContextCompat.getColor(context, R.color.JoystickBase));
        paint2.setColor(ContextCompat.getColor(context, R.color.Joystick));


    }

    /**
     * Set the position of the JoystickBase
     * @param x
     * Position X
     * @param y
     * Position Y
     */
    public void setPosition(float x, float y)
    {
        position.setXY(x,y);
    }



    /**
     * Set the position of the stick of the Joystick
     * @param x
     * @param y
     */
    public void setRelativePosition(float x, float y)
    {

        playerInput = new Vector(x - position.getX(), y - position.getY());

        if(playerInput.magnitude() > baseRadius)
        {
            playerInput.normalise();
            playerInput.setXY(playerInput.getX() *  baseRadius, playerInput.getY()*baseRadius);
            relativePosition.setXY(playerInput.getX() + position.getX(), playerInput.getY() + position.getY());
        }
        else
        {
            relativePosition.setXY(playerInput.getX()  + position.getX(), playerInput.getY()  + position.getY());
        }
    }


    /**
     * Reset the position of the Joystick's stick
     */
    public void resetRelativePosition() {
        relativePosition.setXY(position.getX(), position.getY());
        playerInput = new Vector(0,0);
    }


    /**
     * Get the input of the user
     * @return
     * Vector of the user's input
     */
    public Vector getPlayerInput()
    {
        return playerInput;
    }


    /**
     * Draw the joystick
     * @param canvas
     * Reference of the canvas
     */
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(position.getX(), position.getY(), baseRadius, paint1);
        canvas.drawCircle(relativePosition.getX(), relativePosition.getY(), joystickRadius, paint2);
    }


}

