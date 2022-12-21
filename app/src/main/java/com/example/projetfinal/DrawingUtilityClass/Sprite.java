package com.example.projetfinal.DrawingUtilityClass;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Basic utility class that encapsulate the functionalities of a Sprite
 */
public class Sprite {

    private Bitmap bitmap;
    private Rect rect;

    private int offsetX;
    private int offsetY;

    /**
     * Constructor that initialize the basic parameters of the Sprite
     * @param b
     * ID of the sprite's spritesheet
     * @param r
     * Size and position of the wanted sprite
     */
    public  Sprite(Bitmap b, Rect r)
    {
        bitmap = b;
        rect =r;
        offsetX = rect.width()/2;
        offsetY = rect.height()/2;
    }

    /**
     * Draw the sprite at a given position
     * @param canvas
     * Reference to the canvas
     * @param x
     * X position
     * @param y
     * Y position
     */
    public void draw(Canvas canvas, int x, int y)
    {
        //Offset the drawing to draw the sprite in the middle of the X,Y coordinates
        canvas.drawBitmap(bitmap, rect, new Rect(x - offsetX ,y- offsetY,x+rect.width() - offsetX, y+rect.height()- offsetY), null);
    }


}
