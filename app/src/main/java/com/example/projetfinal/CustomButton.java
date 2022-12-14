package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.Button;


public class CustomButton {




    public abstract class ButtonAction
    {
        public ButtonAction(){}
        public abstract void onClick();
    }

    private Vector position;
    private Vector size;
    private Animateur animateur;
    private ButtonAction buttonAction;

    private boolean ENABLED;

    public CustomButton(int name, int frameR, int numFrame, int numStates, float posX, float posY, int width, int height, Context context)
    {
        ENABLED = true;
        animateur = new Animateur(name, frameR, numFrame, numStates, width, height, context);

        size = new Vector(width, height);
        position = new Vector(posX, posY);


    }

    public void setButtonAction(ButtonAction ba)
    {
        buttonAction = ba;
    }
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



    public void draw(Canvas canvas)
    {
        animateur.draw(canvas, (int)position.getX(), (int)position.getY());
    }

    public void update()
    {
        animateur.update();
    }

    public void onTouchEvent(MotionEvent e) {

        if(ENABLED) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (e.getX() >= position.getX() - size.getX() / 2 && e.getX() <= position.getX() + size.getX() / 2) {
                        if (e.getY() >= position.getY() - size.getY() / 2 && e.getY() <= position.getY() + size.getY() / 2) {
                            animateur.setCurrentState(2);
                        }
                    }
                    return;
                case MotionEvent.ACTION_UP:
                    if (animateur.getCurrentState() == 2) {
                        if (e.getX() >= position.getX() - size.getX() / 2 && e.getX() <= position.getX() + size.getX() / 2) {
                            if (e.getY() >= position.getY() - size.getY() / 2 && e.getY() <= position.getY() + size.getY() / 2) {

                                buttonAction.onClick();

                            }

                        }

                    }
                    animateur.setCurrentState(0);
                    return;
            }

        }
    }
}
