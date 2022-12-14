package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class GameScene extends Scene{

    Player player;
    Joystick joystick;

    ArrayList<Enemy> enemies;
    ArrayList<Vector> coins;
    int coinSize;
    Paint coinGreenPaint;

    Context context;


    CustomButton pause;
    CustomButton restart;
    CustomButton back;
    CustomButton X;
    CustomImage pauseBackground;
    CustomImage gameOver;

    TextPaint scoreTextPaint;
    private int scoreTextSize;

    TextPaint stageTextPaint;
    private int stageTextSize;

    TextPaint coinPaint;
    int coinTextSize;

    Game pointerGame;



    private int SCORE;
    private int STAGE;
    private int STAGECOUNTER;
    private int ENEMYLIMIT;
    private int ENEMYSTEP;

    int PAUSE;  // PAUSE = 0 : Le jeu fonctionne
                // PAUSE = 1 : Pause reguliere
                // Pause = 2 : GAME OVER

    public GameScene(Context context, Game gameRef)
    {
        SCORE = 0;
        coinTextSize = 50;
        scoreTextSize = 75;
        stageTextSize = 200;
        STAGE=0;
        STAGECOUNTER = 0;
        ENEMYLIMIT = 10;
        ENEMYSTEP = 5;
        PAUSE = 0;
        coinSize = 25;

        this.context =context;

        pointerGame = gameRef;


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

        player = new Player(context, Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2);
        joystick = new Joystick(context);
        enemies = new ArrayList<Enemy>();
        coins = new ArrayList<Vector>();

        pause = new CustomButton(R.drawable.pausespritesheet, 5, 3, 3,              70, 70, 100, 100, context);

        pauseBackground = new CustomImage(R.drawable.pausebackgroundspritesheet, 5, 3, 1,      Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2,600,800, context);
        restart = new CustomButton(R.drawable.restartspritesheet, 5, 3, 3,              Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 + 200, 400, 200, context);
        back = new CustomButton(R.drawable.backspritesheet, 5, 3, 3,              Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2, 400, 200, context);
        X =new CustomButton(R.drawable.xspritesheet, 5, 3, 3,              Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 - 200, 200, 200, context);
        gameOver = new CustomImage(R.drawable.gameoverspritesheet, 5, 3, 1,      Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 - 250,400,150, context);



        pause.setButtonAction(pause.new ButtonAction()
        {

            @Override
            public void onClick() {
                PAUSE = 1;
            }
        });

        restart.setButtonAction(restart.new ButtonAction()
        {

            @Override
            public void onClick() {
                pointerGame.switchScene(1);
            }
        });

        back.setButtonAction(back.new ButtonAction() {
            @Override
            public void onClick() {
                pointerGame.switchScene(0);
            }
        });

        X.setButtonAction(X.new ButtonAction() {
            @Override
            public void onClick() {
                PAUSE = 0;
            }
        });
    }







    @Override
    public void onTouchEvent(MotionEvent e) {


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
        else if(PAUSE == 1)
        {
            X.onTouchEvent(e);
            restart.onTouchEvent(e);
            back.onTouchEvent(e);
        }
        else
        {
            restart.onTouchEvent(e);
            back.onTouchEvent(e);
        }






    }

    @Override
    public void draw(Canvas canvas) {

        for(int i =0; i < coins.size(); i++)
        {
            canvas.drawCircle(coins.get(i).getX(), coins.get(i).getY(), coinSize, coinGreenPaint);
        }

        for(int i =0; i < enemies.size(); i++)
        {
            enemies.get(i).draw(canvas);
        }

        player.draw(canvas);

        joystick.draw(canvas);
        pause.draw(canvas);

        canvas.drawText(String.valueOf(SCORE), Scene.canvasSize.getX() - scoreTextSize/2,  scoreTextSize, scoreTextPaint);
        canvas.drawText("Coins: "  + Game.getCoins(), Scene.canvasSize.getX()/2, coinTextSize, coinPaint);

        if(STAGECOUNTER < 75)
        {
            canvas.drawText("STAGE " + String.valueOf(STAGE), Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2, stageTextPaint);
            STAGECOUNTER++;
        }

        if(PAUSE == 1)
        {
            pauseBackground.draw(canvas);
            restart.draw(canvas);
            back.draw(canvas);
            X.draw(canvas);
        }
        //GAMEOVER
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
            canvas.drawText("Score:" + String.valueOf(SCORE), Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2 - 115, txtPaint);
            restart.draw(canvas);
            back.draw(canvas);
        }

    }

    @Override
    public void update() {


        if(PAUSE == 0) {
            SCORE++;
            if(SCORE % 1000 == 0){STAGE++; STAGECOUNTER = 0; ENEMYLIMIT += ENEMYSTEP;}
            else if(SCORE % 333 == 0 && coins.size() < 2)
            {
                Random r = new Random();
                coins.add(new Vector(r.nextInt((int)Scene.canvasSize.getX() - coinSize*2) + coinSize, r.nextInt((int)Scene.canvasSize.getY() - coinSize*2) + coinSize ));
            }












            for (int i = enemies.size()-1; i >=0; i--) {

                enemies.get(i).update(GameLoop.getAverageUPS() / 1000);
                if(enemies.get(i).testCollision(player.getPosition(), player.getSize() ))
                {
                    //CHECK SI LE SCORE EST PLUS GRAND QUE HIGHSCORE
                    //UPDATE LE NOMBRE DE PIECE
                    PAUSE = 2;
                    break;
                }
                if(enemies.get(i).outOfBounds())
                {
                    enemies.remove(i);
                }
            }

            for(int i = coins.size()-1; i >=0; i--)
            {
                Vector diff = new Vector(coins.get(i).getX() - player.getPosition().getX(), coins.get(i).getY() - player.getPosition().getY());
                if(diff.magnitude() < player.getSize() + coinSize)
                {
                    coins.remove(i);
                    Game.addCoin();
                }
            }


            while(enemies.size() < ENEMYLIMIT)
            {
                enemies.add(addEnemy(STAGE));
            }

            joystick.update();
            player.update(joystick.getPlayerInput(), GameLoop.getAverageUPS());


            pause.update();
        }
        else if(PAUSE == 1)
        {
            pauseBackground.update();
            restart.update();
            back.update();
            X.update();
        }
        //GameOver update
        else
        {
            gameOver.update();
            pauseBackground.update();
            restart.update();
            back.update();
        }

    }




    private Enemy addEnemy(int stageLevel)
    {
        Random r = new Random();
        if(STAGE == 0)
        {
            int sizeX = r.nextInt(100) + 100;
            int sizeY = r.nextInt(100) + 100;
            int speed = r.nextInt(20) + 20;
            return new EnemyStraight(context, new Vector(sizeX,sizeY), speed);
        }
        else if(STAGE == 1 || STAGE ==2)
        {
            int select = r.nextInt(2);
            if(select == 0)
            {
                int sizeX = r.nextInt(100 + (STAGE*5)) + 100;
                int sizeY = r.nextInt(100 + (STAGE*5)) + 100;
                int speed = r.nextInt(20+(STAGE*5)) + 20;
                return new EnemyStraight(context, new Vector(sizeX,sizeY), speed);

            }
            else
            {
                int sizeX = r.nextInt(20 + (STAGE*5)) + 30;
                int sizeY = r.nextInt(20 + (STAGE*5)) + 30;
                int speed = r.nextInt(20+(STAGE*5)) + 5;
                float amplitude = r.nextInt(9000) + 3000;
                return new EnemyCurve(context, new Vector(sizeX,sizeY), speed,amplitude);

            }
        }
        else if(STAGE == 3 || STAGE ==4)
        {
            int select = r.nextInt(2);
            if(select == 0)
            {
                int sizeX = r.nextInt(100 + (STAGE*10)) + 100;
                int sizeY = r.nextInt(100 + (STAGE*10)) + 100;
                int speed = r.nextInt(20+(STAGE*10)) + 20;
                return new EnemyStraight(context, new Vector(sizeX,sizeY), speed);

            }
            else
            {
                int sizeX = r.nextInt(20 + (STAGE*10)) + 40;
                int sizeY = r.nextInt(20 + (STAGE*10)) + 40;
                int speed = r.nextInt(20+(STAGE*10)) + 10;
                float amplitude = r.nextInt(3000) + 3000;
                return new EnemyCurve(context, new Vector(sizeX,sizeY), speed,amplitude);

            }
        }
        else if(STAGE == 5 || STAGE == 6)
        {
            int sizeX = r.nextInt(30 + (STAGE*15)) + 20;
            int sizeY = r.nextInt(30 + (STAGE*15)) + 20;
            int speed = r.nextInt(20 + (STAGE)) + 3;
            float amplitude = r.nextInt(200) + 200;
            float periode = r.nextInt(10 * STAGE) + 35;
            return new EnemySin(context, new Vector(sizeX,sizeY), speed,periode,amplitude);
        }
        else if(STAGE == 7 || STAGE == 8)
        {
            int select = r.nextInt(2);
            if(select == 0)
            {
                int sizeX = r.nextInt(100 + (STAGE*15)) + 100;
                int sizeY = r.nextInt(100 + (STAGE*15)) + 100;
                int speed = r.nextInt(20+(STAGE*2)) + 20;
                return new EnemyStraight(context, new Vector(sizeX,sizeY), speed);

            }
            else
            {
                int sizeX = r.nextInt(30 + (STAGE*15)) + 20;
                int sizeY = r.nextInt(30 + (STAGE*15)) + 20;
                int speed = r.nextInt(20 + (STAGE)) + 3;
                float amplitude = r.nextInt(200) + 200;
                float periode = r.nextInt(10 * STAGE) + 35;
                return new EnemySin(context, new Vector(sizeX,sizeY), speed,periode,amplitude);

            }
        }
        else if(STAGE == 9 || STAGE == 10)
        {
            int select = r.nextInt(2);
            if(select == 0)
            {
                int sizeX = r.nextInt(20 + (STAGE*10)) + 40;
                int sizeY = r.nextInt(20 + (STAGE*10)) + 40;
                int speed = r.nextInt(20+(STAGE*2)) + 10;
                float amplitude = r.nextInt(STAGE * 500) + 3000;
                return new EnemyCurve(context, new Vector(sizeX,sizeY), speed,amplitude);

            }
            else
            {
                int sizeX = r.nextInt(30 + (STAGE*15)) + 20;
                int sizeY = r.nextInt(30 + (STAGE*15)) + 20;
                int speed = r.nextInt(20 + (STAGE)) + 3;
                float amplitude = r.nextInt(200) + 200;
                float periode = r.nextInt(10 * STAGE) + 35;
                return new EnemySin(context, new Vector(sizeX,sizeY), speed,periode,amplitude);

            }
        }
        else
        {

            int select = r.nextInt(3);
            if(select == 0)
            {
                int sizeX = r.nextInt(100 + (STAGE*15)) + 100;
                int sizeY = r.nextInt(100 + (STAGE*15)) + 100;
                int speed = r.nextInt(20+(STAGE*2)) + 20;
                return new EnemyStraight(context, new Vector(sizeX,sizeY), speed);
            }
            else if(select == 1)
            {
                int sizeX = r.nextInt(20 + (STAGE*10)) + 40;
                int sizeY = r.nextInt(20 + (STAGE*10)) + 40;
                int speed = r.nextInt(20+(STAGE*2)) + 10;
                float amplitude = r.nextInt(STAGE * 500) + 3000;
                return new EnemyCurve(context, new Vector(sizeX,sizeY), speed,amplitude);

            }
            else
            {
                int sizeX = r.nextInt(30 + (STAGE*15)) + 20;
                int sizeY = r.nextInt(30 + (STAGE*15)) + 20;
                int speed = r.nextInt(20 + (STAGE)) + 3;
                float amplitude = r.nextInt(200) + 200;
                float periode = r.nextInt(10 * STAGE) + 35;
                return new EnemySin(context, new Vector(sizeX,sizeY), speed,periode,amplitude);

            }
        }
    }



}
