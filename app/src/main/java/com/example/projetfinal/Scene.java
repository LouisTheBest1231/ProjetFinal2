package com.example.projetfinal;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Scene {

    public static Vector canvasSize;

    public abstract void onTouchEvent(MotionEvent e);
    public abstract void draw(Canvas canvas);
    public abstract void update();


}
