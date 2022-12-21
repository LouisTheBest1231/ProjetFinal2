package com.example.projetfinal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Abstract class of Enemy to allow polymorphism of the different variations of the Enemy
 */
public abstract class Enemy {

    //Private settings of an Enenmy
    private Vector position;
    private Vector startPosition;
    protected Vector size;
    protected Paint paint;


    private float speed;
    private double angle;
    private float avance;


    //abstract method to overide by the children's class
    abstract float moveFunction(float f);


    /**
     * Constructor that initialize the necessary values
     */
    public Enemy()
    {
        avance = 0;
        speed = 1;

    }

    /**
     * Children's function to setup the speed of the Enemy
     * @param s
     * Speed unit
     */
    protected void setSpeed(float s)
    {
        speed = s;
    }

    /**
     * Children's function to setup the starting parameters of the Enemy (position and direction of movement)
     */
    protected void setStartParameters()
    {

        int random = new Random().nextInt(4);
        double start;double end;double r;

        //Select the position of where the Enemy will spawn (left, up, right, down)
        switch (random)
        {
            case 0:
                //Mur de gauche
                start = 3*Math.PI/2;
                end = 5*Math.PI/2;
                r = new Random().nextDouble();

                //Setup the angle of the movement based on the starting position
                angle = start + (r*(end-start));

                //Setup the starting position
                startPosition = new Vector(-size.getX(), (float)new Random().nextDouble() * Scene.getCanvas().getY());
                position = startPosition;

                break;

            case 1:
                //Mur de haut
                start = 0;
                end = Math.PI;
                r = new Random().nextDouble();
                //Setup the angle of the movement based on the starting position
                angle = start + (r*(end-start));

                //Setup the starting position
                startPosition = new Vector((float)new Random().nextDouble() * Scene.getCanvas().getX() , -size.getY());
                position = startPosition;
                break;

            case 2:
                //Mur de droite
                start = Math.PI/2;
                end = 3* Math.PI/2;
                r = new Random().nextDouble();
                //Setup the angle of the movement based on the starting position
                angle = start + (r*(end-start));

                //Setup the starting position
                startPosition = new Vector(size.getX() + Scene.getCanvas().getX(), (float)new Random().nextDouble() * Scene.getCanvas().getY());
                position = startPosition;
                break;

            case 3:
                //Mur de bas;
                start = Math.PI;
                end = Math.PI*2;
                r = new Random().nextDouble();
                //Setup the angle of the movement based on the starting position
                angle = start + (r*(end-start));

                //Setup the starting position
                startPosition = new Vector((float)new Random().nextDouble() * Scene.getCanvas().getX() , size.getY()+ Scene.getCanvas().getY());
                position = startPosition;
                break;
        }
    }


    /**
     * Update the Enemy's position
     * @param deltaTime
     * Elapsed time
     */
    public void update(double deltaTime)
    {
        //Modify the step of the Enemy based on the speed and the deltaTime (in order to prevent framerate from scaling the Enemy's speed)
        avance += deltaTime * speed;

        //Calculate the new Position
        float x = avance;
        //calculate the Y based on the custom abstract function
        float y = moveFunction(x);

        //Move the player
        position = new Vector((float)(x * Math.cos(angle) + y * Math.sin(angle) + startPosition.getX()), (float)(-x*Math.sin(angle) + y*Math.cos(angle) + startPosition.getY()));
    }


    /**
     * Draw the Enemy based on it's size, position and paint
     * @param canvas
     * Reference to the canvas
     */
    public void draw(Canvas canvas)
    {
        //Draw the rect at his center (this is why we have to shift the positions by /2)
        Rect r = new Rect((int)(position.getX() - size.getX()/2), (int)(position.getY()-size.getY()/2), (int)(position.getX()+size.getX()/2), (int)(position.getY()+size.getY()/2));
        canvas.drawRect(r, paint);
    }


    /**
     * Check if the Enemy is out the screen
     * @return
     * True if out, false if in
     */
    public boolean outOfBounds()
    {
        if(position.getY() > Scene.getCanvas().getY() + 300 || position.getY() < -300)
        {
            return true;
        }
        else if(position.getX() > Scene.getCanvas().getX() + 300 || position.getX() < -300)
        {
            return true;
        }

        return false;

    }


    /**
     * Test if there is a collision with the spherical player
     * @param playerPos
     * Position of the player
     * @param playerSize
     * Size of the player (radius)
     * @return
     * True if collision, False if no collision
     */
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
