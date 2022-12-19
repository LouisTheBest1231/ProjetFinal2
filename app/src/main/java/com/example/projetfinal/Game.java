package com.example.projetfinal;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

public class Game extends SurfaceView implements SurfaceHolder.Callback {


    private int SOUNDVALUE = 5;

    private static int COINS = 100;

    private GameLoop gameLoop;

    private static UserDao userDao;

    Scene currentScene;

    MainMenuScene m1;
    GameScene g1;
    SettingScene s1;
    CustomiseScene c1;


    public Game(Context context) {
        super(context);




        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);




        setFocusable(true);



        AppDatabase db;
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userDao = db.userDao();
        if (userDao.getAll().size() == 0) { User user = new User(0, 100); userDao.insert(user); }



    }



    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        Game.COINS = 100;
        Scene.canvasSize = new Vector(this.getWidth(), this.getHeight());
        m1 = new MainMenuScene(getContext(), this);
        s1 = new SettingScene(getContext(), this, SOUNDVALUE);
        c1 = new CustomiseScene(getContext(), this);

        currentScene = m1;

        gameLoop = new GameLoop(this, surfaceHolder);
        gameLoop.startLoop();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        currentScene.onTouchEvent(event);
        /*
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                joystick.setPosition(event.getX(), event.getY());
                return true;

            case MotionEvent.ACTION_MOVE:
                joystick.setRelativePosition(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_UP:
                joystick.resetRelativePosition();
                return true;
        }
        */

        return true;
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);


        currentScene.draw(canvas);



        //drawUPS(canvas);
        //drawFPS(canvas);
    }

    public void drawUPS(Canvas canvas)
    {

        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS :" + averageUPS, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas)
    {

        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();

        int color = ContextCompat.getColor(getContext(), R.color.magenta);

        paint.setColor(color);
        paint.setTextSize(50);

        canvas.drawText("FPS :" + averageFPS, 100, 200, paint);
    }

    public void update()
    {

        currentScene.update();
    }
    public void switchScene(int scene)
    {
        switch(scene)
        {
            case 0 :
                currentScene = m1;
                return;

            case 1 :
                g1 = new GameScene(getContext(), this);
                currentScene = g1;
                return;
            case 2:

                c1.updateOnce();
                currentScene = c1;
                return;
            case 3:

                currentScene = s1;
                return;
        }

    }

    public void setSoundValue(int soundValue) {
        SOUNDVALUE = soundValue;
    }

    public static void addCoin()
    {
        COINS++;
    }
    public static int getCoins()
    {
        return COINS;
    }
    public static void useCoin(int amount)
    {
        COINS  = Math.max(COINS-amount, 0);
    }
    public static UserDao database() {return userDao;}

}

