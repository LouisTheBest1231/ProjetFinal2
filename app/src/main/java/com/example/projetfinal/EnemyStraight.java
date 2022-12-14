package com.example.projetfinal;

import android.content.Context;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class EnemyStraight extends Enemy{



    public EnemyStraight(Context context, Vector size, float speed)
    {
        super();


        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.yellow));
        this.size = new Vector(size.getX(), size.getY());

        setSpeed(speed);

        setStartParameters();
    }

    @Override
    float moveFunction(float f) {
        return f;
    }
}
