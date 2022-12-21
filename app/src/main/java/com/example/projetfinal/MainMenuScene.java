package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

/**
 * Child of SCENE - MainMenuScene - Encapsulate all the functionalities of the MainMenu
 */
public class MainMenuScene  extends Scene{
    private Paint testPaint;
    private CustomButton playButton;
    private CustomButton customizeButton;
    private CustomButton settingsButton;
    private CustomButton quitButton;
    private CustomImage titre;
    private Game pointerGame;



    MediaPlayer mediaPlayerButtonSound;

    /**
     * Constructor of MainMenuScene that Initialize the objects of the MENU
     * @param context
     * Reference to the Activity Context
     * @param pointerGame
     * Reference to the GAME object
     */
    public MainMenuScene(Context context, Game pointerGame) {



        mediaPlayerButtonSound = MediaPlayer.create(context, R.raw.press_sound1);



        testPaint = new Paint();
        testPaint.setColor(ContextCompat.getColor(context, R.color.white));

        //Set up all the buttons with their respective positions
        titre = new CustomImage(R.drawable.gamespritesheet, 7, 3, 1,                    Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 - 600,800,400, context);
        playButton = new CustomButton(R.drawable.playspritesheet, 5, 3, 3,              Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 , 300, 200, context);
        customizeButton = new CustomButton(R.drawable.customisespritesheet, 5, 3, 3,    Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 + 200, 500, 200, context);
        settingsButton = new CustomButton(R.drawable.settingsspritesheet, 5, 3, 3,      Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 + 400, 400, 200, context);
        quitButton = new CustomButton(R.drawable.quitspritesheet, 5, 3, 3,              Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 + 600, 400, 200, context);

        //Set up the GAME pointer
        this.pointerGame = pointerGame;



        //Set up the button ACTIONS
        playButton.setButtonAction(playButton.new ButtonAction()
        {

            @Override
            public void onClick() {
                MainMenuScene.this.pointerGame.switchScene(1);
                mediaPlayerButtonSound.start();
            }
        });


        customizeButton.setButtonAction(customizeButton.new ButtonAction()
        {

            @Override
            public void onClick() {
                MainMenuScene.this.pointerGame.switchScene(2);
                mediaPlayerButtonSound.start();
            }
        });

        settingsButton.setButtonAction(settingsButton.new ButtonAction()
        {

            @Override
            public void onClick() {
                MainMenuScene.this.pointerGame.switchScene(3);
                mediaPlayerButtonSound.start();
            }
        });

        quitButton.setButtonAction(quitButton.new ButtonAction()
        {

            @Override
            public void onClick() {
                System.exit(0);mediaPlayerButtonSound.start();
            }
        });




    }

    /**
     * Update based on the user's input
     * @param e
     * MotionEvent of the user
     */
    @Override
    public void onTouchEvent(MotionEvent e) {

        playButton.onTouchEvent(e);
        customizeButton.onTouchEvent(e);
        settingsButton.onTouchEvent(e);
        quitButton.onTouchEvent(e);
    }

    /**
     * Drawing all the objects of the MENU
     * @param canvas
     * Reference to the CANVAS
     */
    @Override
    public void draw(Canvas canvas) {

        titre.draw(canvas);
        playButton.draw(canvas);
        customizeButton.draw(canvas);
        settingsButton.draw(canvas);
        quitButton.draw(canvas);


    }

    /**
     * Updating all the objects of the MENU
     *
     */
    @Override
    public void update() {
        titre.update();
        playButton.update();
        customizeButton.update();
        settingsButton.update();
        quitButton.update();

    }

    /**
     * Cleanup and remove from heap
     */
    @Override
    public void cleanup() {
        mediaPlayerButtonSound.release();
    }
}
