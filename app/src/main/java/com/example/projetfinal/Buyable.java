package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.MotionEvent;

public class Buyable {

    private int price;

    private CustomButton select;
    private CustomButton buy;

    private CustomButton currentButton;
    private CustomImage skin;

    private TextPaint pricePaint;
    private int priceTextSize;

    private boolean ISBOUGHT;


    private CustomiseScene parentScene;

    private int BUYABLEINDEX;

    public Buyable(int price, int spritesheet, int frameR, int numFrame, int width, int height, Context context, CustomiseScene parentScene, int index )
    {
        this.price = price;
        priceTextSize=100;
        BUYABLEINDEX = index;


        this.parentScene = parentScene;

        //
        ISBOUGHT = false;
        //

        skin = new CustomImage(spritesheet, frameR, numFrame, 1, Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2, 70,70, context);

        select = new CustomButton(R.drawable.selectspritesheet, 5,3,3, Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 + 300, 400,200, context);
        buy = new CustomButton(R.drawable.buyspritesheet, 5,3,3, Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 + 400, 400,200, context);

        pricePaint = new TextPaint();
        pricePaint.setAntiAlias(true);
        pricePaint.setTextSize(priceTextSize);
        pricePaint.setTextAlign(Paint.Align.CENTER);
        pricePaint.setColor(0xFFFFFFFF);



        select.setButtonAction(select.new ButtonAction() {
            @Override
            public void onClick() {

                Player.setSkin(skin);
                //Set select selon BUYABLEINDEX

            }
        });

        buy.setButtonAction(buy.new ButtonAction() {
            @Override
            public void onClick() {
                //Set skin achete selon BUYABLEINDEX
                buy();
            }
        });



        currentButton = buy;



    }

    public void giveBuyable()
    {
        ISBOUGHT = true;
        Player.setSkin(skin);

    }

    public void buy()
    {
        ISBOUGHT = true;
        //UPDATE COINS QUAND ACHETE
        Game.useCoin(price);
        parentScene.updateOnce();

    }

    public void onTouchEvent(MotionEvent e)
    {
        currentButton.onTouchEvent(e);
    }

    public void draw(Canvas canvas)
    {
        if(!ISBOUGHT){canvas.drawText("Price: " + price,Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 + 200, pricePaint );}
        skin.draw(canvas);
        currentButton.draw(canvas);
    }

    public void updateShopOnce()
    {
        if(ISBOUGHT)
        {

            currentButton = select;


        }
        else
        {
            currentButton = buy;
            if(Game.getCoins() < price)
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
    public void update()
    {

        skin.update();
        currentButton.update();

    }

}
