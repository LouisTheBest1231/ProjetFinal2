package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

public class MainMenuScene  extends Scene{
    Paint testPaint;
    CustomButton playButton;
    CustomButton customizeButton;
    CustomButton settingsButton;
    CustomButton quitButton;
    CustomImage titre;
    Game parentGame;





    public MainMenuScene(Context context, Game pointerGame) {
        testPaint = new Paint();
        testPaint.setColor(ContextCompat.getColor(context, R.color.white));

        titre = new CustomImage(R.drawable.gamespritesheet, 7, 3, 1,                    Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 - 600,800,400, context);
        playButton = new CustomButton(R.drawable.playspritesheet, 5, 3, 3,              Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 , 300, 200, context);
        customizeButton = new CustomButton(R.drawable.customisespritesheet, 5, 3, 3,    Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 + 200, 500, 200, context);
        settingsButton = new CustomButton(R.drawable.settingsspritesheet, 5, 3, 3,      Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 + 400, 400, 200, context);
        quitButton = new CustomButton(R.drawable.quitspritesheet, 5, 3, 3,              Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 + 600, 400, 200, context);

        parentGame = pointerGame;



        playButton.setButtonAction(playButton.new ButtonAction()
        {

            @Override
            public void onClick() {
                parentGame.switchScene(1);
            }
        });


        customizeButton.setButtonAction(customizeButton.new ButtonAction()
        {

            @Override
            public void onClick() {
                parentGame.switchScene(2);
            }
        });

        settingsButton.setButtonAction(settingsButton.new ButtonAction()
        {

            @Override
            public void onClick() {
                parentGame.switchScene(3);
            }
        });

        quitButton.setButtonAction(quitButton.new ButtonAction()
        {

            @Override
            public void onClick() {
                System.exit(0);
            }
        });




    }

    @Override
    public void onTouchEvent(MotionEvent e) {

        playButton.onTouchEvent(e);
        customizeButton.onTouchEvent(e);
        settingsButton.onTouchEvent(e);
        quitButton.onTouchEvent(e);
    }

    @Override
    public void draw(Canvas canvas) {

        titre.draw(canvas);
        playButton.draw(canvas);
        customizeButton.draw(canvas);
        settingsButton.draw(canvas);
        quitButton.draw(canvas);


    }

    @Override
    public void update() {
        titre.update();
        playButton.update();
        customizeButton.update();
        settingsButton.update();
        quitButton.update();

    }
}
