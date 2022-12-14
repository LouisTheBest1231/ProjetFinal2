package com.example.projetfinal;

import android.content.Context;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class EnemyCurve extends Enemy{

    private float amplitude = 0.006f;

    public EnemyCurve(Context context, Vector size, float speed, float amplitude)
    {
        super();

        this.amplitude = 1/amplitude;

        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.cyan));
        this.size = new Vector(size.getX(),size.getY());

        //setSpeed(20);
        setSpeed(speed);


        setStartParameters();
    }

    @Override
    float moveFunction(float f) {
        return (float) (amplitude * Math.pow(f/10, 2));
    }
}
