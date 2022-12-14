package com.example.projetfinal;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Joystick {
    private Vector position;
    private Vector relativePosition;
    private Vector playerInput;


    private final float baseRadius = 80;
    private final float joystickRadius = 40;

    private final Paint paint1;
    private final Paint paint2;




    public Joystick(Context context)
    {
        playerInput = new Vector(0,0);
        position = new Vector(-50,-50);
        relativePosition= new Vector(-500,-500);
        paint1 = new Paint();
        paint2 = new Paint();
        paint1.setColor(ContextCompat.getColor(context, R.color.JoystickBase));
        paint2.setColor(ContextCompat.getColor(context, R.color.Joystick));


    }


    public void setPosition(float x, float y)
    {
        position.setXY(x,y);
    }
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
    public void resetRelativePosition() {
        relativePosition.setXY(position.getX(), position.getY());
        playerInput = new Vector(0,0);
    }

    public Vector getPlayerInput()
    {
        return playerInput;
    }




    public void update()
    {

    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(position.getX(), position.getY(), baseRadius, paint1);
        canvas.drawCircle(relativePosition.getX(), relativePosition.getY(), joystickRadius, paint2);
    }


}

