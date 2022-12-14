package com.example.projetfinal;

import static android.util.Log.println;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.io.Console;

public class Animateur {

    private Bitmap bitmap;
    private Sprite[][] sprites;

    private int currentFrame;
    private int currentState;

    private int frameRate;

    private int frameCounter;

    public Animateur(int name,int frameR,  int numFrame, int numStates, int width, int height, Context context)
    {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), name, bitmapOptions);


        Sprite[][] spriteArray = new Sprite[numFrame][numStates];
        for(int i =0; i < numFrame; i++)
        {
            for(int j =0; j < numStates; j++)
            {
                spriteArray[i][j] = new Sprite(bitmap, new Rect(width * i, height*j, width*i + width, height*j+height));
            }
        }
        sprites = spriteArray;


        frameRate = 60/frameR;
        frameCounter = 0;
        currentState =0;
        currentFrame = 0;

    }

    public void draw(Canvas canvas, int x ,int y)
    {
        sprites[currentFrame][currentState].draw(canvas, x,y);
    }


    public void update()
    {
        frameCounter++;
        if(frameCounter > frameRate)
        {
            currentFrame = (currentFrame + 1) % sprites.length;
            frameCounter=0;
        }
    }

    public void setCurrentState(int state)
    {

        currentState = Math.min(state, sprites[0].length-1);
    }
    public int getCurrentState()
    {
        return currentState;
    }
}
