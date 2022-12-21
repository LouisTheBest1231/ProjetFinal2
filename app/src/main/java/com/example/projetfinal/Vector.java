package com.example.projetfinal;

/**
 * Utility class that Encapsulate the properties of a Vector
 */
public class Vector {

    //Private value of a 2D vector
    private float positionX;
    private float positionY;


    /**
     * Constructor that initialize the vector's value
     * @param x
     * Value of Vector X
     * @param y
     * Value of Vector Y
     */
    public Vector(float x, float y)
    {
        positionX = x;
        positionY = y;
    }

    /**
     * Setter of Vector
     * @param x
     * Value of Vector X
     * @param y
     * Value of Vector Y
     */
    public void setXY(float x, float y)
    {
        positionX = x;
        positionY = y;
    }

    /**
     * Normalises the Vector (reduces it's length to 1u)
     */
    public void normalise()
    {
        double r = magnitude();
        positionY/=r;
        positionX/=r;
    }

    /**
     * Get the length of the Vector
     * @return
     * length of the Vector
     */
    public double magnitude()
    {
        return Math.sqrt(positionX*positionX + positionY*positionY);
    }

    /**
     * Getter of the Value of Vector X
     * @return
     * X
     */
    public float getX()
    {
        return positionX;
    }

    /**
     * Setter of the Value of Vector Y
     * @return
     * Y
     */
    public float getY()
    {
        return positionY;
    }

}
