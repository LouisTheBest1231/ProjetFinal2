package com.example.projetfinal.Enemy;

import android.content.Context;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.projetfinal.R;
import com.example.projetfinal.Utilities.Vector;

/**
 * Child class of an Enemy - Magenta Enemy that moves in sin
 */
public class EnemySin extends Enemy{

    private double amplitude;
    private double periode;

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
    public EnemySin(Context context, Vector size, float speed, float amplitude, float periode)
    {
        super();

        this.amplitude = amplitude;
        this.periode = periode;
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.magenta));
        this.size = new Vector(size.getX(),size.getY());
        setSpeed(speed);
        setStartParameters();

    }
    /**
     * Overriden moveFunction that transforms the movement into a sin function
     * @param f
     * Position (x) on the function
     * @return
     */
    @Override
    float moveFunction(float f) {
        return (float)(Math.sin(f/periode) * amplitude);
    }
}
