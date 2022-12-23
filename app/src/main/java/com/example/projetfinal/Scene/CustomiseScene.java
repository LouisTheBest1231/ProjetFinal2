package com.example.projetfinal.Scene;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.example.projetfinal.DrawingUtilityClass.Buyable;
import com.example.projetfinal.DrawingUtilityClass.CustomButton;
import com.example.projetfinal.Game;
import com.example.projetfinal.R;

import java.util.ArrayList;

/**
 * Encapsulate all the functionality of the class that allows the User to change skin
 */
public class CustomiseScene extends Scene {

    private CustomButton back;
    private CustomButton left;
    private CustomButton right;

    private ArrayList<Buyable> skins;
    private int skinIndex;

    private Game pointerGame;

    private TextPaint coinPaint;
    final private int coinTextSize = 100;

    private MediaPlayer mediaPlayerButton;



    /**
     * Constructor - set up all the objects and all the setting of the CustomiseScene
     * @param context
     * Reference to the context of the Activity
     * @param gameRef
     * Reference of the GAME object
     */
    public CustomiseScene(Context context, Game gameRef, Boolean[] skinsUnlocked)
    {

        mediaPlayerButton = MediaPlayer.create(context, R.raw.press_sound2);

        pointerGame = gameRef;

        skinIndex = 0;
        skins = new ArrayList<Buyable>();


        coinPaint = new TextPaint();
        coinPaint.setAntiAlias(true);
        coinPaint.setTextSize(coinTextSize);
        coinPaint.setTextAlign(Paint.Align.CENTER);
        coinPaint.setColor(0xFFFFFFFF);


        //Setup of the Buttons
        back = new CustomButton(R.drawable.backspritesheet, 5,3,3,220,Scene.getCanvas().getY()-120,400,200, context);
        left = new CustomButton(R.drawable.flechegauchespritesheet, 5,3,3, Scene.getCanvas().getX()/2 - 400, Scene.getCanvas().getY()/2, 200,200,context);
        right = new CustomButton(R.drawable.flechedroitespritesheet, 5,3,3, Scene.getCanvas().getX()/2 + 400, Scene.getCanvas().getY()/2, 200,200,context);


        //Setup of all the buyables
        skins.add(new Buyable(0, R.drawable.baseskinspritesheet, 5, 1, 70,70,context,this, pointerGame, 0));
        skins.get(0).giveBuyable();
        skins.add(new Buyable(50, R.drawable.skintestspritesheet, 5, 4, 70,70,context, this, pointerGame,1));
        skins.add(new Buyable(100, R.drawable.skin1, 5, 1, 70,70,context,this, pointerGame, 2));
        skins.add(new Buyable(100, R.drawable.skin2, 5, 1, 70,70,context,this, pointerGame, 3));
        skins.add(new Buyable(120, R.drawable.skin3, 5, 1, 70,70,context,this, pointerGame, 4));
        skins.add(new Buyable(150, R.drawable.skin4, 5, 1, 70,70,context,this, pointerGame, 5));
        skins.add(new Buyable(175, R.drawable.skin5, 5, 1, 70,70,context,this, pointerGame, 6));
        skins.add(new Buyable(190, R.drawable.skin6, 5, 1, 70,70,context,this, pointerGame, 7));
        skins.add(new Buyable(200, R.drawable.skin7, 5, 1, 70,70,context,this, pointerGame, 8));
        skins.add(new Buyable(250, R.drawable.skin8, 5, 1, 70,70,context,this, pointerGame, 9));

        for (int i = 0; i < 10; i++) {
            if (skinsUnlocked[i] == true) { skins.get(i).giveBuyable(); }
        }


        //Setup of the buttons actions
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
                mediaPlayerButton.start();
            }
        });

        right.setButtonAction(right.new ButtonAction() {
            @Override
            public void onClick() {
                skinIndex = ((skinIndex + 1)%skins.size() + skins.size())% skins.size();
                mediaPlayerButton.start();
            }
        });

    }

    /**
     * Update of the user's input
     * @param e
     * MotionEvent of the user
     */
    @Override
    public void onTouchEvent(MotionEvent e) {
        back.onTouchEvent(e);
        left.onTouchEvent(e);
        right.onTouchEvent(e);
        skins.get(skinIndex).onTouchEvent(e);
    }

    /**
     * Drawing all objects in the CustomiseScene
     * @param canvas
     * Reference to the CANVAS
     */
    @Override
    public void draw(Canvas canvas) {

        back.draw(canvas);
        left.draw(canvas);
        right.draw(canvas);
        skins.get(skinIndex).draw(canvas);
        canvas.drawText("Coins: "  + pointerGame.getCoins(), Scene.getCanvas().getX()/2, coinTextSize, coinPaint);
    }

    /**
     * Update all objects in the CustomiseScene
     */
    @Override
    public void update() {
        back.update();
        left.update();
        right.update();
        skins.get(skinIndex).update();
    }

    /**
     * Cleanup and remove from heap
     */
    @Override
    public void cleanup() {
        mediaPlayerButton.release();
        for(int i =0; i < skins.size(); i++)
        {
            skins.get(i).cleanup();
        }
    }

    /**
     * Update the states of all the buyable objects
     */
    public void updateOnce()
    {
        for(int i =0; i < skins.size(); i++)
        {
            skins.get((i)).updateShopOnce();;

        }
    }

    /**
     * Reset the selected states of all the buyable objects (allow to always have a SINGLE object selected at all time)
     */
    public void resetSelect()
    {
        for(int i =0 ; i < skins.size(); i++)
        {
            skins.get(i).resetSelect();
        }
    }

    /**
     * Get the amount of coin of the USER
     * @return
     * Amount of coins
     */
    public int getCoin()
    {
        return pointerGame.getCoins();
    }

    /**
     * Use (Subtract) a certain amount of coins
     * @param amount
     * Amount to subtract
     */
    public void useCoin(int amount)
    {
        pointerGame.useCoin(amount);
    }
}
