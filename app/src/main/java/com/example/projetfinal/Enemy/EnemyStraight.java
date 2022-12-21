package com.example.projetfinal.Enemy;

import android.content.Context;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.projetfinal.R;
import com.example.projetfinal.Utilities.Vector;

/**
 * Child class of an Enemy - Yellow Enemy that moves in straight line
 */
public class EnemyStraight extends Enemy{


    /**
     * Constructor that setup the initial settings
     * @param context
     * Reference to the context of the Activity
     * @param size
     * Vector of the size of the Enemy
     * @param speed
     * Unit of speed of the Enemy
     */
    public EnemyStraight(Context context, Vector size, float speed)
    {
        super();
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.yellow));
        this.size = new Vector(size.getX(), size.getY());
        setSpeed(speed);
        setStartParameters();
    }

    /**
     * Overriden moveFunction that transforms the movement into a straight line
     * @param f
     * Position (x) on the function
     * @return
     */
    @Override
    float moveFunction(float f) {
        return f;
    }
}
