package com.example.projetfinal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public abstract class Enemy {

    private Vector position;
    private Vector startPosition;
    protected Vector size;
    protected Paint paint;


    private float speed;
    private double angle;
    private float avance;

    abstract float moveFunction(float f);

    public Enemy()
    {
        avance = 0;
        speed = 1;




    }

    protected void setSpeed(float s)
    {
        speed = s;
    }

    protected void setStartParameters()
    {
        //Determine l'angle de depart
        int random = new Random().nextInt(4);
        double start;double end;double r;
        switch (random)
        {
            case 0:
                //Mur de gauche
                start = 3*Math.PI/2;
                end = 5*Math.PI/2;
                r = new Random().nextDouble();
                angle = start + (r*(end-start));


                startPosition = new Vector(-size.getX(), (float)new Random().nextDouble() * Scene.canvasSize.getY());
                position = startPosition;

                break;

            case 1:
                //Mur de haut
                start = 0;
                end = Math.PI;
                r = new Random().nextDouble();
                angle = start + (r*(end-start));

                startPosition = new Vector((float)new Random().nextDouble() * Scene.canvasSize.getX() , -size.getY());
                position = startPosition;
                break;

            case 2:
                //Mur de droite
                start = Math.PI/2;
                end = 3* Math.PI/2;
                r = new Random().nextDouble();
                angle = start + (r*(end-start));

                startPosition = new Vector(size.getX() + Scene.canvasSize.getX(), (float)new Random().nextDouble() * Scene.canvasSize.getY());
                position = startPosition;
                break;

            case 3:
                //Mur de bas;
                start = Math.PI;
                end = Math.PI*2;
                r = new Random().nextDouble();
                angle = start + (r*(end-start));

                startPosition = new Vector((float)new Random().nextDouble() * Scene.canvasSize.getX() , size.getY()+ Scene.canvasSize.getY());
                position = startPosition;
                break;
        }
    }







    public void update(double deltaTime)
    {
        avance += deltaTime * speed;
        float x = avance;
        float y = moveFunction(x);

        position = new Vector((float)(x * Math.cos(angle) + y * Math.sin(angle) + startPosition.getX()), (float)(-x*Math.sin(angle) + y*Math.cos(angle) + startPosition.getY()));
    }

    public void draw(Canvas canvas)
    {
        Rect r = new Rect((int)(position.getX() - size.getX()/2), (int)(position.getY()-size.getY()/2), (int)(position.getX()+size.getX()/2), (int)(position.getY()+size.getY()/2));
        canvas.drawRect(r, paint);
    }


    public boolean outOfBounds()
    {
        if(position.getY() > Scene.canvasSize.getY() + 300 || position.getY() < -300)
        {
            return true;
        }
        else if(position.getX() > Scene.canvasSize.getX() + 300 || position.getX() < -300)
        {
            return true;
        }

        return false;

    }


    public boolean testCollision(Vector playerPos, float playerSize)
    {
        float testX = playerPos.getX();
        float testY = playerPos.getY();

        if(playerPos.getX() < position.getX() - size.getX()/2)
        {
            testX = position.getX() - size.getX()/2;
        }
        else if(playerPos.getX() > position.getX() + size.getX()/2)
        {
            testX = position.getX() + size.getX()/2;
        }

        if(playerPos.getY() < position.getY() - size.getY()/2)
        {
            testY = position.getY() - size.getY()/2;
        }
        else if(playerPos.getY() > position.getY() + size.getY()/2)
        {
            testY = position.getY()  + size.getY() /2;
        }


        float distX = playerPos.getX()-testX;
        float distY = playerPos.getY() - testY;
        double distance = Math.sqrt((distX*distX) + (distY*distY));


        if(distance <= playerSize)
        {
            return true;
        }

        return false;

    }










}
