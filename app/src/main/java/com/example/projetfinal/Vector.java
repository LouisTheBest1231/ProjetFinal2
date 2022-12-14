package com.example.projetfinal;


public class Vector {

    private float positionX;
    private float positionY;


    public Vector(float x, float y)
    {
        positionX = x;
        positionY = y;
    }

    public void setXY(float x, float y)
    {
        positionX = x;
        positionY = y;
    }

    public void normalise()
    {
        double r = magnitude();
        positionY/=r;
        positionX/=r;
    }

    public double magnitude()
    {
        return Math.sqrt(positionX*positionX + positionY*positionY);
    }

    public float getX()
    {
        return positionX;
    }
    public float getY()
    {
        return positionY;
    }

}
