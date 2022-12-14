package com.example.projetfinal;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private float positionX;
    private float positionY;

    private float size;


    private final float speed = 4;

    private static CustomImage skin;

    public Player(Context context, float x, float y)
    {
        positionX =x;
        positionY=y;
        size = 35;


    }

    public void draw(Canvas canvas)
    {
        skin.draw(canvas);
    }
    public void update(Vector playerInput, double deltatime)
    {
        deltatime /= 1000;
        positionX += playerInput.getX() * speed * deltatime;
        positionY += playerInput.getY() * speed * deltatime;


        skin.setPosition(positionX,positionY);
        skin.update();
        collisionUpdate();
    }




    public static void setSkin(CustomImage skinRef)
    {
        skin = new CustomImage(skinRef);
    }














    public void setPosition(float x, float y)
    {
        positionX=x;
        positionY=y;
    }

    public Vector getPosition()
    {
        return new Vector(positionX,positionY);
    }
    public float getSize()
    {
        return size;
    }




    private void collisionUpdate()
    {
        if(positionX > Scene.canvasSize.getX())
        {
            positionX = Scene.canvasSize.getX();
        }
        else if(positionX < 0){positionX = 0;}

        if(positionY > Scene.canvasSize.getY())
        {
            positionY = Scene.canvasSize.getY();
        }
        else if(positionY < 0){positionY = 0;}

    }


}

