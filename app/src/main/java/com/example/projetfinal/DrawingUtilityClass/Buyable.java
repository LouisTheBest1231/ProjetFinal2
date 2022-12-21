package com.example.projetfinal.DrawingUtilityClass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.example.projetfinal.Game;
import com.example.projetfinal.R;
import com.example.projetfinal.Scene.CustomiseScene;
import com.example.projetfinal.Scene.Scene;
import com.example.projetfinal.User;

/**
 * Encapsulate the objects that can be bought in the game (CustomizeScene)
 */
public class Buyable {

    private int price;

    private CustomButton select;
    private CustomButton buy;

    private CustomButton currentButton;
    private CustomImage skin;

    private TextPaint pricePaint;
    final private int priceTextSize = 100;

    private boolean ISBOUGHT;
    private boolean ISSELECTED;


    MediaPlayer mediaPlayerButton;
    MediaPlayer mediaPlayerBuy;

    private CustomiseScene parentScene;

    private int BUYABLEINDEX;

    /**
     * Constructor to initialize the animated objects and the functionality of the BUYABLE OBJECTS
     * @param price
     * Price of the buyable
     * @param spritesheet
     * Spritesheet of the skin of the buyable
     * @param frameR
     * FrameRate of the skin of the buyable
     * @param numFrame
     * Number of frames of the skin of the buyable
     * @param width
     * Width of a sprite
     * @param height
     * Height of a sprite
     * @param context
     * Reference to the context of the Activity
     * @param parentScene
     * Reference to the CUSTOMISESCENE parent object
     * @param index
     * index of the buyable
     */
    public Buyable(int price, int spritesheet, int frameR, int numFrame, int width, int height, Context context, CustomiseScene parentScene, Game pointerGame, int index )
    {
        this.price = price;
        BUYABLEINDEX = index;
        this.parentScene = parentScene;


        mediaPlayerButton = MediaPlayer.create(context, R.raw.press_sound2);
        mediaPlayerBuy = MediaPlayer.create(context, R.raw.buy_sound);

        //
        ISBOUGHT = false;
        ISSELECTED = false;
        //


        //Setup all the animated objects
        skin = new CustomImage(spritesheet, frameR, numFrame, 1, Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2, 70,70, context);

        select = new CustomButton(R.drawable.selectspritesheet, 5,3,3, Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 + 300, 400,200, context);
        buy = new CustomButton(R.drawable.buyspritesheet, 5,3,3, Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 + 400, 400,200, context);


        //Setup paint for price text
        pricePaint = new TextPaint();
        pricePaint.setAntiAlias(true);
        pricePaint.setTextSize(priceTextSize);
        pricePaint.setTextAlign(Paint.Align.CENTER);
        pricePaint.setColor(0xFFFFFFFF);



        //Setup all buttons functionalities
        select.setButtonAction(select.new ButtonAction() {
            @Override
            public void onClick() {

                parentScene.resetSelect();
                ISSELECTED = true;
                parentScene.updateOnce();
                pointerGame.setPlayerSkin(skin);
                mediaPlayerButton.start();

                //Set select selon BUYABLEINDEX
                User tempUser = Game.database().findById(1);// + sound
                tempUser.setSelectedSkin(BUYABLEINDEX);
                Game.database().delete(Game.database().findById(1));
                Game.database().insert(tempUser);

            }
        });

        buy.setButtonAction(buy.new ButtonAction() {
            @Override
            public void onClick() {

                mediaPlayerBuy.start();

                //Set skin achete selon BUYABLEINDEX
                User tempUser = Game.database().findById(1);// + sound
                Boolean tempSkin[] = tempUser.getSkins(); tempSkin[BUYABLEINDEX] = true;
                tempUser.setSkins(tempSkin);
                Game.database().delete(Game.database().findById(1));
                Game.database().insert(tempUser);

                buy();

            }
        });


        //The base button for a skin is the BUY button
        currentButton = buy;



    }


    /**
     * Unlocks skin for free (only used for first skin (the base one))
     */

    public void giveBuyable(Game pointerGame)
    {
        //Check to make sure it is the FIRST skin
        if(BUYABLEINDEX == 0) {
            ISBOUGHT = true;
            ISSELECTED = true;

            pointerGame.setPlayerSkin(skin);
        }
    }

    /**
     * Reset the select state to allow only one single buyable to be selected
     */
    public void resetSelect()
    {
        ISSELECTED = false;
        select.setENABLED(true, 0);
    }


    /**
     * Action of buying the BUYABLE, changes the button and reduces the amount of coins
     */
    private void buy()
    {
        ISBOUGHT = true;

        //UPDATE COINS QUAND ACHETE
        parentScene.useCoin(price);

        //UPDATE data base with new amount of coins are with new unlocked skin
        User tempUser = Game.database().findById(1);// + sound
        tempUser.setPieces(tempUser.getPieces() - price);
        Game.database().delete(Game.database().findById(1));
        Game.database().insert(tempUser);


        //Amount of coins diminished so we have to update all the buyables again
        parentScene.updateOnce();

    }


    /**
     * Update on the user's input
     * @param e
     * MotionEvent of the user
     */
    public void onTouchEvent(MotionEvent e)
    {
            currentButton.onTouchEvent(e);

    }

    /**
     *
     * @param canvas
     */
    public void draw(Canvas canvas)
    {
        if(!ISBOUGHT){canvas.drawText("Price: " + price,Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 + 200, pricePaint );}
        skin.draw(canvas);
        currentButton.draw(canvas);
    }

    /**
     * Update the state of the buyable based on his information
     */
    public void updateShopOnce()
    {

        //If selected, disable the button
        if(ISSELECTED)
        {
            select.setENABLED(false, 2);
        }

        //If is bought, set that the currentButton is the select buttton
        if(ISBOUGHT)
        {

            currentButton = select;


        }
        //If not selected and not bought, current button is buy
        else
        {
            currentButton = buy;
            if(parentScene.getCoin() < price)
            {
                buy.setENABLED(false,1);
                pricePaint.setColor(0xFFFF0000);
            }
            else
            {
                buy.setENABLED(true,1);
                pricePaint.setColor(0xFFFFFFFF);
            }
        }

    }

    /**
     * Update the objects of the Buyable
     */
    public void update()
    {

        skin.update();
        currentButton.update();

    }

    /**
     * Cleanup memory from heap
     */
    public void cleanup()
    {
        mediaPlayerButton.release();
        mediaPlayerBuy.release();
    }

}
