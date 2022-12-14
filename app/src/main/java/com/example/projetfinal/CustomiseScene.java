package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.MotionEvent;

import java.util.ArrayList;

public class CustomiseScene extends Scene{

    CustomButton back;
    CustomButton left;
    CustomButton right;

    ArrayList<Buyable> skins;
    int skinIndex;

    Game pointerGame;

    private TextPaint coinPaint;
    private int coinTextSize;


    public CustomiseScene(Context context, Game gameRef)
    {
        pointerGame = gameRef;

        skinIndex = 0;
        skins = new ArrayList<Buyable>();
        coinTextSize = 100;
        coinPaint = new TextPaint();
        coinPaint.setAntiAlias(true);
        coinPaint.setTextSize(coinTextSize);
        coinPaint.setTextAlign(Paint.Align.CENTER);
        coinPaint.setColor(0xFFFFFFFF);


        back = new CustomButton(R.drawable.backspritesheet, 5,3,3,220,Scene.canvasSize.getY()-120,400,200, context);
        left = new CustomButton(R.drawable.flechegauchespritesheet, 5,3,3, Scene.canvasSize.getX()/2 - 400, Scene.canvasSize.getY()/2, 200,200,context);
        right = new CustomButton(R.drawable.flechedroitespritesheet, 5,3,3, Scene.canvasSize.getX()/2 + 400, Scene.canvasSize.getY()/2, 200,200,context);

        skins.add(new Buyable(0, R.drawable.baseskinspritesheet, 5, 1, 70,70,context,this));
        skins.get(0).giveBuyable();
        skins.add(new Buyable(50, R.drawable.skintestspritesheet, 5, 4, 70,70,context, this));
        skins.add(new Buyable(75, R.drawable.skintestspritesheet, 5, 4, 70,70,context, this));
        skins.add(new Buyable(100, R.drawable.skintestspritesheet, 5, 4, 70,70,context, this ));
        skins.add(new Buyable(200, R.drawable.skintestspritesheet, 5, 4, 70,70,context, this));

        back.setButtonAction(back.new ButtonAction() {
            @Override
            public void onClick() {
                pointerGame.switchScene(0);
            }
        });

        left.setButtonAction(left.new ButtonAction() {
            @Override
            public void onClick() {
                skinIndex = ((skinIndex - 1)%skins.size() + skins.size())% skins.size();
            }
        });

        right.setButtonAction(right.new ButtonAction() {
            @Override
            public void onClick() {
                skinIndex = ((skinIndex + 1)%skins.size() + skins.size())% skins.size();
            }
        });

    }

    @Override
    public void onTouchEvent(MotionEvent e) {
        back.onTouchEvent(e);
        left.onTouchEvent(e);
        right.onTouchEvent(e);
        skins.get(skinIndex).onTouchEvent(e);
    }

    @Override
    public void draw(Canvas canvas) {

        back.draw(canvas);
        left.draw(canvas);
        right.draw(canvas);
        skins.get(skinIndex).draw(canvas);
        canvas.drawText("Coins: "  + Game.getCoins(), Scene.canvasSize.getX()/2, coinTextSize, coinPaint);
    }

    @Override
    public void update() {
        back.update();
        left.update();
        right.update();
        skins.get(skinIndex).update();
    }

    public void updateOnce()
    {
        for(int i =0; i < skins.size(); i++)
        {
            skins.get((i)).updateShopOnce();;

        }
    }
}
