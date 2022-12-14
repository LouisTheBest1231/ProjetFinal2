package com.example.projetfinal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private Bitmap bitmap;
    private Rect rect;

    private int offsetX;
    private int offsetY;

    public  Sprite(Bitmap b, Rect r)
    {
        bitmap = b;
        rect =r;
        offsetX = rect.width()/2;
        offsetY = rect.height()/2;
    }

    public void draw(Canvas canvas, int x, int y)
    {
        canvas.drawBitmap(bitmap, rect, new Rect(x - offsetX ,y- offsetY,x+rect.width() - offsetX, y+rect.height()- offsetY), null);
    }


}
