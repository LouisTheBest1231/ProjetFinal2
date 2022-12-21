package com.example.projetfinal;

import android.content.Context;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

/**
 * Child class of an Enemy - Cyan Enemy that moves in curves
 */
public class EnemyCurve extends Enemy{

    private float amplitude = 0.006f;

    /**
     * Constructor that setup the initial settings
     * @param context
     * Reference to the context of the Activity
     * @param size
     * Vector of the size of the Enemy
     * @param speed
     * Unit of speed of the Enemy
     * @param amplitude
     * Amplitude of the Curve of the Enemy
     */
    public EnemyCurve(Context context, Vector size, float speed, float amplitude)
    {
        super();

        //Sets the necessery parameters
        this.amplitude = 1/amplitude;
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.cyan));
        this.size = new Vector(size.getX(),size.getY());
        setSpeed(speed);
        setStartParameters();
    }

    /**
     * Overriden moveFunction that transforms the movement into parable
     * @param f
     * Position (x) on the function
     * @return
     */
    @Override
    float moveFunction(float f) {
        return (float) (amplitude * Math.pow(f/10, 2));
    }
}
