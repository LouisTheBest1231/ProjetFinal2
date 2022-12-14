package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;

public class CustomImage {

    private Vector position;
    private Vector size;
    private Animateur animateur;


    public CustomImage(int name, int frameR, int numFrame, int numStates, float posX, float posY, int width, int height, Context context)
    {

        animateur = new Animateur(name, frameR, numFrame, numStates, width, height, context);

        size = new Vector(width, height);
        position = new Vector(posX, posY);


    }
    public CustomImage(CustomImage c)
    {
        animateur = c.animateur;
        size = new Vector(c.size.getX(), c.size.getY());
        position = new Vector(c.position.getX(), c.position.getY());
    }
    public void draw(Canvas canvas)
    {
        animateur.draw(canvas, (int)position.getX(), (int)position.getY());
    }

    public void update()
    {
        animateur.update();
    }


    public void setPosition(float x, float y){position = new Vector(x,y);}

    public void setAnimateurState(int state)
    {
        animateur.setCurrentState(state);
    }
    public int getAnimateurState()
    {
        return animateur.getCurrentState();
    }
}
