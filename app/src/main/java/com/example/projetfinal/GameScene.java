package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.text.TextPaint;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;

/**
 * Game class - Encapsulate the heart of the GAME and all of the functionalities
 */
public class GameScene extends Scene{



    private Context context;


    private CustomButton pause;
    private CustomButton restart;
    private CustomButton back;
    private CustomButton X;
    private CustomImage pauseBackground;
    private CustomImage gameOver;


    private Player player;
    private Joystick joystick;
    private Spawner spawner;


    private ArrayList<Vector> coins;


    private Paint coinGreenPaint;
    final private  int coinSize = 25;

    private TextPaint scoreTextPaint;
    final private int scoreTextSize = 75;

    private TextPaint stageTextPaint;
    final private int stageTextSize = 200;

    private TextPaint coinPaint;
    final private int coinTextSize = 50;

    private Game pointerGame;



    private int SCORE;
    private int STAGE;
    private int STAGECOUNTER;
    private int ENEMYLIMIT;
    final private int ENEMYSTEP = 5;

    private int PAUSE;  // PAUSE = 0 : Le jeu fonctionne
                // PAUSE = 1 : Pause reguliere
                // Pause = 2 : GAME OVER


    MediaPlayer mediaPlayerDie;
    MediaPlayer mediaPlayerCoin;
    MediaPlayer mediaPlayerButton;
    MediaPlayer mediaPlayerHighscore;

    /**
     * Game constructor that initialize all the objects and starts the game
     * @param context
     * reference of the activity Context
     * @param gameRef
     * Reference of the GAME object
     */
    public GameScene(Context context, Game gameRef, CustomImage playerSkin)
    {

        //Initialize variables

        mediaPlayerDie = MediaPlayer.create(context, R.raw.death_sound);
        mediaPlayerCoin = MediaPlayer.create(context, R.raw.coincollect_sound);
        mediaPlayerButton = MediaPlayer.create(context, R.raw.press_sound1);
        mediaPlayerHighscore= MediaPlayer.create(context, R.raw.highscore_sound);

        SCORE = 0;
        STAGE=0;
        STAGECOUNTER = 0;
        ENEMYLIMIT = 10;
        PAUSE = 0;

        this.context =context;

        pointerGame = gameRef;


        //Setting all the paints
        scoreTextPaint = new TextPaint();
        scoreTextPaint.setAntiAlias(true);
        scoreTextPaint.setTextSize(scoreTextSize);
        scoreTextPaint.setTextAlign(Paint.Align.RIGHT);
        scoreTextPaint.setColor(0xFFFFFFFF);

        stageTextPaint = new TextPaint();
        stageTextPaint.setAntiAlias(true);
        stageTextPaint.setTextSize(stageTextSize);
        stageTextPaint.setTextAlign(Paint.Align.CENTER);
        stageTextPaint.setColor(0xFFFFFFFF);


        coinPaint = new TextPaint();
        coinPaint.setAntiAlias(true);
        coinPaint.setTextSize(coinTextSize);
        coinPaint.setTextAlign(Paint.Align.CENTER);
        coinPaint.setColor(0xFFFFFFFF);

        coinGreenPaint = new Paint();
        coinGreenPaint.setAntiAlias(true);
        coinGreenPaint.setColor(ContextCompat.getColor(context, R.color.green));





        //Initalize all objects and arrays
        player = new Player(playerSkin, context, Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2);
        joystick = new Joystick(context);
        spawner = new Spawner();
        coins = new ArrayList<Vector>();

        pause = new CustomButton(R.drawable.pausespritesheet, 5, 3, 3,              70, 70, 100, 100, context);

        pauseBackground = new CustomImage(R.drawable.pausebackgroundspritesheet, 5, 3, 1,      Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2,600,800, context);
        restart = new CustomButton(R.drawable.restartspritesheet, 5, 3, 3,              Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 + 200, 400, 200, context);
        back = new CustomButton(R.drawable.backspritesheet, 5, 3, 3,              Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2, 400, 200, context);
        X =new CustomButton(R.drawable.xspritesheet, 5, 3, 3,              Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 - 200, 200, 200, context);
        gameOver = new CustomImage(R.drawable.gameoverspritesheet, 5, 3, 1,      Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 - 250,400,150, context);



        //Set the buttonAction on the buttons
        pause.setButtonAction(pause.new ButtonAction()
        {

            @Override
            public void onClick() {
                PAUSE = 1;
                mediaPlayerButton.start();
            }
        });

        restart.setButtonAction(restart.new ButtonAction()
        {

            @Override
            public void onClick() {
                pointerGame.switchScene(1);
                mediaPlayerButton.start();
            }
        });

        back.setButtonAction(back.new ButtonAction() {
            @Override
            public void onClick() {
                pointerGame.switchScene(0);
                mediaPlayerButton.start();
            }
        });

        X.setButtonAction(X.new ButtonAction() {
            @Override
            public void onClick() {
                PAUSE = 0;
                mediaPlayerButton.start();
            }
        });
    }






    /**
     * Update based on the User's input in the GAMESCENE
     * @param e
     * MotionEvent of the User
     */
    @Override
    public void onTouchEvent(MotionEvent e) {


        //If the game is running, update the player and the joystick
        if(PAUSE == 0) {
            pause.onTouchEvent(e);
            switch(e.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    joystick.setPosition(e.getX(), e.getY());


                    return;

                case MotionEvent.ACTION_MOVE:
                    joystick.setRelativePosition(e.getX(), e.getY());
                    return;
                case MotionEvent.ACTION_UP:
                    joystick.resetRelativePosition();
                    return;
            }
        }
        //If the game is on pause, update only the buttons of the little menu
        else if(PAUSE == 1)
        {
            X.onTouchEvent(e);
            restart.onTouchEvent(e);
            back.onTouchEvent(e);
        }
        //If it is GAMEOVER, update only the buttons of the little menu
        else
        {
            restart.onTouchEvent(e);
            back.onTouchEvent(e);
        }


    }





    /**
     * Drawing all the objects in the GameScene
     * @param canvas
     * Reference to the canvas
     */
    @Override
    public void draw(Canvas canvas) {

        for(int i =0; i < coins.size(); i++)
        {
            canvas.drawCircle(coins.get(i).getX(), coins.get(i).getY(), coinSize, coinGreenPaint);
        }

        spawner.draw(canvas);

        player.draw(canvas);

        joystick.draw(canvas);
        pause.draw(canvas);

        canvas.drawText(String.valueOf(SCORE), Scene.getCanvas().getX() - scoreTextSize/2,  scoreTextSize, scoreTextPaint);
        canvas.drawText("Coins: "  + pointerGame.getCoins(), Scene.getCanvas().getX()/2, coinTextSize, coinPaint);

        //Draw for 75 frames after changing the STAGE
        if(STAGECOUNTER < 75)
        {
            canvas.drawText("STAGE " + String.valueOf(STAGE), Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2, stageTextPaint);
            STAGECOUNTER++;
        }

        //PAUSE - IF the game is pause, continue to draw the background but add the menu
        if(PAUSE == 1)
        {
            pauseBackground.draw(canvas);
            restart.draw(canvas);
            back.draw(canvas);
            X.draw(canvas);
        }
        //GAMEOVER - If the game is over, continue to draw the background but add the menu of the game over
        else if(PAUSE == 2)
        {
            pauseBackground.draw(canvas);
            gameOver.draw(canvas);
            TextPaint txtPaint = new TextPaint();
            txtPaint = new TextPaint();
            txtPaint.setAntiAlias(true);
            txtPaint.setTextSize(50);
            txtPaint.setTextAlign(Paint.Align.CENTER);
            txtPaint.setColor(0xFFFFFFFF);
            canvas.drawText("Score:" + String.valueOf(SCORE), Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2 - 115, txtPaint);
            restart.draw(canvas);
            back.draw(canvas);
        }

    }

    /**
     * Update the gameScene
     */
    @Override
    public void update() {


        //NOPAUSE - If the game is running, update the game
        if(PAUSE == 0) {
            //Update score
            SCORE++;
            //If the score reach a certain amount, increase the enemy limit and the stage level
            if(SCORE % 1000 == 0){STAGE++; STAGECOUNTER = 0; ENEMYLIMIT += ENEMYSTEP;}

            //Add coin if the conditions are checked
            else if(SCORE % 333 == 0 && coins.size() < 2)
            {
                Random r = new Random();
                coins.add(new Vector(r.nextInt((int)Scene.getCanvas().getX() - coinSize*2) + coinSize, r.nextInt((int)Scene.getCanvas().getY() - coinSize*2) + coinSize ));
            }


           //Update the enemies in the spawner
           if(spawner.update(pointerGame, player.getPosition(), player.getSize()))
           {
               PAUSE = 2;
               mediaPlayerDie.start();


               //Change the highscore of the database
               User tempUser = Game.database().findById(1);// + sound
               if (tempUser.getScore() < SCORE) { tempUser.setScore(SCORE); }
               tempUser.setPieces(tempUser.getPieces() + pointerGame.getCoins());
               Game.database().delete(Game.database().findById(1));
               Game.database().insert(tempUser);

           }

            //Collision check with the coins
            for(int i = coins.size()-1; i >=0; i--)
            {
                Vector diff = new Vector(coins.get(i).getX() - player.getPosition().getX(), coins.get(i).getY() - player.getPosition().getY());
                if(diff.magnitude() < player.getSize() + coinSize)
                {
                    mediaPlayerCoin.start();
                    coins.remove(i);
                    pointerGame.addCoin();
                }
            }


            //Spawn new enemies if the enemy count is below ENEMYLIMIT
            while(spawner.getEnemiesSize() < ENEMYLIMIT)
            {
                spawner.addEnemy(STAGE, context);
            }

            player.update(joystick.getPlayerInput(), pointerGame.getAverageUPS());
            pause.update();



        }
        //PAUSE - Update the necessary buttons
        else if(PAUSE == 1)
        {
            pauseBackground.update();
            restart.update();
            back.update();
            X.update();
        }
        //GAMEOVER - Update the necessary buttons
        else
        {


            gameOver.update();
            pauseBackground.update();
            restart.update();
            back.update();
        }

    }


    /**
     * Cleanup and remove from heap
     */
    @Override
    public void cleanup() {
        mediaPlayerDie.release();
        mediaPlayerCoin.release();
        mediaPlayerButton.release();
        mediaPlayerHighscore.release();
    }


}
