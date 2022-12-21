package com.example.projetfinal;

import static android.util.Log.println;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.io.Console;

/**
 * Utility class that is the foundation for the animated objects of the application
 */
public class Animateur {

    private Bitmap bitmap;
    private Sprite[][] sprites;

    private int currentFrame;
    private int currentState;

    private int frameRate;

    private int frameCounter;

    /**
     * Constructor to setup all the settings of the Animateur
     * @param name
     * ID of the spriteSheet
     * @param frameR
     * Wanted FrameRate of the animation
     * @param numFrame
     * Amount of frames on the spriteSheet (nbr of columns)
     * @param numStates
     * Amount of states on the spriteSheet (nbr of row)
     * @param width
     * Width of a single sprite on the spriteSheet
     * @param height
     * Height of a single sprite on the spriteSheet
     * @param context
     * Reference to the Activity Context
     */
    public Animateur(int name,int frameR,  int numFrame, int numStates, int width, int height, Context context)
    {
        //Creates the bitmap
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), name, bitmapOptions);
        /*
        try {
            bitmap = BitmapFactory.decodeResource(context.getResources(), name, bitmapOptions);
        }
        catch (OutOfMemoryError e)
        {
            System.out.println("\n\n IMPORTANT");
            System.out.println(e);
        }

         */

        //Creates all the sprites and store them in the spriteArray[][]
        Sprite[][] spriteArray = new Sprite[numFrame][numStates];
        for(int i =0; i < numFrame; i++)
        {
            for(int j =0; j < numStates; j++)
            {
                spriteArray[i][j] = new Sprite(bitmap, new Rect(width * i, height*j, width*i + width, height*j+height));
            }
        }
        sprites = spriteArray;


        //Set the frame step, we could reference the time in the GAMELOOP, but it is unnecessery because the wanted frameRate is a constant 60FPS
        frameRate = 60/frameR;

        //Initial value of the counters
        frameCounter = 0;
        currentState =0;
        currentFrame = 0;

    }

    /**
     * Draw the current wanted sprite to a given position
     * @param canvas
     * Reference to the canvas
     * @param x
     * Position X
     * @param y
     * Position Y
     */
    public void draw(Canvas canvas, int x ,int y)
    {
        sprites[currentFrame][currentState].draw(canvas, x,y);
    }


    /**
     * Updates the frameCount
     */
    public void update()
    {
        frameCounter++;
        //Increment the currentFrame if the counter has reached it's limit
        if(frameCounter > frameRate)
        {
            currentFrame = (currentFrame + 1) % sprites.length;
            frameCounter=0;
        }
    }

    /**
     * Change the state (row) of the sprites used
     * @param state
     * State wanted
     */
    public void setCurrentState(int state)
    {
        //Change state (can't allow more than sprites[0].length-1, otherwise massive crash )
        currentState = Math.min(state, sprites[0].length-1);
    }

    /**
     * Returns current state
     * @return
     * Current state
     */
    public int getCurrentState()
    {
        return currentState;
    }
}
