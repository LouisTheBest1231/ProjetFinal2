package com.example.projetfinal.Scene;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.projetfinal.Utilities.Vector;

/**
 * Abstract class Scene to allow polymorphism of the drawing of the SCENES
 */
public abstract class Scene {

    //Public static variable to the size of the drawing canvas
    //It is static with public static setters and getter because it is a value that is really necessary and required for almost every subclasses of the application
    private static Vector canvasSize;

    //Methods to override
    public abstract void onTouchEvent(MotionEvent e);
    public abstract void draw(Canvas canvas);
    public abstract void update();
    public abstract void cleanup();



    /**
     * Get the canvas Vector value
     * @return
     * Vector of the width and height of the canvas
     */
    public  static Vector getCanvas()
    {
        return canvasSize;
    }

    /**
     * Set the canvas size and heigh
     * @param x
     * Canvas width
     * @param y
     * Canvas height
     */
    public static void setCanvasSize(int x, int y)
    {
        canvasSize = new Vector(x,y);
    }


}


