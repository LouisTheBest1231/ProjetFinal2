package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Class of an Animated custom image
 */
public class CustomImage {

    private Vector position;
    private Vector size;
    private Animateur animateur;


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
     * @param posX
     * Position X of the image
     * @param posY
     * Position Y of the image
     * @param width
     * Width of a single sprite on the spriteSheet
     * @param height
     * Height of a single sprite on the spriteSheet
     * @param context
     * Reference to the Activity Context
     */
    public CustomImage(int name, int frameR, int numFrame, int numStates, float posX, float posY, int width, int height, Context context)
    {

        animateur = new Animateur(name, frameR, numFrame, numStates, width, height, context);

        size = new Vector(width, height);
        position = new Vector(posX, posY);


    }

    /**
     * Copying constructor
     * @param c
     * CustomImage that you want to copy
     */
    public CustomImage(CustomImage c)
    {
        animateur = c.animateur;
        size = new Vector(c.size.getX(), c.size.getY());
        position = new Vector(c.position.getX(), c.position.getY());
    }

    /**
     * Draw the image at a given position
     * @param canvas
     * Reference to the Canvas
     */
    public void draw(Canvas canvas)
    {
        animateur.draw(canvas, (int)position.getX(), (int)position.getY());
    }

    /**
     * Update the animateur
     */
    public void update()
    {
        animateur.update();
    }


    /**
     * Set the position of the image
     * @param x
     * Position X
     * @param y
     * Position Y
     */
    public void setPosition(float x, float y){position = new Vector(x,y);}

    /**
     * Modify the state of the Animateur
     * @param state
     */
    public void setAnimateurState(int state)
    {
        animateur.setCurrentState(state);
    }

    /**
     * Get the current Animateur state
     * @return
     */
    public int getAnimateurState()
    {
        return animateur.getCurrentState();
    }
}
