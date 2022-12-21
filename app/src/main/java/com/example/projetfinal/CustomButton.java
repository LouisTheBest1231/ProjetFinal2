package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Class of a custom animated and functioning button
 */
public class CustomButton {


    /**
     * Inline class to override when creating the button to set the action of the BUTTONCLICK
     */
    public abstract class ButtonAction
    {
        /**
         * Neutral constructor
         */
        public ButtonAction(){}

        /**
         * Method to override for the BUTTONCLICK
         */
        public abstract void onClick();
    }


    private Vector position;
    private Vector size;
    private Animateur animateur;
    private ButtonAction buttonAction;

    private boolean ENABLED;

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
    public CustomButton(int name, int frameR, int numFrame, int numStates, float posX, float posY, int width, int height, Context context)
    {
        ENABLED = true;
        animateur = new Animateur(name, frameR, numFrame, numStates, width, height, context);

        size = new Vector(width, height);
        position = new Vector(posX, posY);


    }

    /**
     * Set the buttonAction to a new Button Action
     * @param ba
     * ButtonAction reference
     */
    public void setButtonAction(ButtonAction ba)
    {
        buttonAction = ba;
    }


    /**
     * Set the state of the Button and if it is ENABLED
     * @param b
     * ENABLED boolean
     * @param state
     * Wanted state
     */
    public void setENABLED(boolean b, int state)
    {
        if(b)
        {
            ENABLED = true;
            animateur.setCurrentState(0);
        }
        else
        {
            ENABLED = false;
            animateur.setCurrentState(state);
        }
    }


    /**
     * Draw the button at his position
     * @param canvas
     * reference to the canvas
     */
    public void draw(Canvas canvas)
    {
        animateur.draw(canvas, (int)position.getX(), (int)position.getY());
    }

    /**
     * Update the animateur of the Button
     */
    public void update()
    {
        animateur.update();
    }


    /**
     * Check if the user is pressing on the button and update it's state in consequence
     * @param e
     */
    public void onTouchEvent(MotionEvent e) {

        if(ENABLED) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (e.getX() >= position.getX() - size.getX() / 2 && e.getX() <= position.getX() + size.getX() / 2) {
                        if (e.getY() >= position.getY() - size.getY() / 2 && e.getY() <= position.getY() + size.getY() / 2) {
                            //If pressed, change state
                            animateur.setCurrentState(2);
                        }
                    }
                    return;
                case MotionEvent.ACTION_UP:
                    if (animateur.getCurrentState() == 2) {
                        if (e.getX() >= position.getX() - size.getX() / 2 && e.getX() <= position.getX() + size.getX() / 2) {
                            if (e.getY() >= position.getY() - size.getY() / 2 && e.getY() <= position.getY() + size.getY() / 2) {

                                //If button released, activate onClick
                                try
                                {
                                    buttonAction.onClick();
                                }
                                catch (Exception exception)
                                {
                                    //The onClick action was empty or is simply not working
                                    System.out.println(exception);
                                    return;
                                }


                            }

                        }

                    }
                    //Button was released elsewhere AND the button is still enabled, set the button state back to normal
                    if(ENABLED){
                    animateur.setCurrentState(0);}
                    return;
            }

        }
    }
}
