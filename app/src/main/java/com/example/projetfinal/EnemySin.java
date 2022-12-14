package com.example.projetfinal;

import android.content.Context;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class EnemySin extends Enemy{

    private double amplitude;
    private double periode;

    public EnemySin(Context context, Vector size, float speed, float amplitude, float periode)
    {
        super();
        this.amplitude = amplitude;
        this.periode = periode;

        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.magenta));
        this.size = new Vector(size.getX(),size.getY());


        setSpeed(speed);
        //setSpeed(20);

        setStartParameters();
    }

    @Override
    float moveFunction(float f) {
        return (float)(Math.sin(f/periode) * amplitude);
    }
}
