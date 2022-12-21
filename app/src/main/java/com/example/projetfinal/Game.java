package com.example.projetfinal;


import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.projetfinal.DrawingUtilityClass.CustomImage;
import com.example.projetfinal.Scene.CustomiseScene;
import com.example.projetfinal.Scene.GameScene;
import com.example.projetfinal.Scene.MainMenuScene;
import com.example.projetfinal.Scene.Scene;
import com.example.projetfinal.Scene.SettingScene;
import com.example.projetfinal.Utilities.SongPlayer;

/**
 * Game object which is a self containing instance of the gameengine
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {


    private int SOUNDVALUE = 5;
    private int COINS = 100;
    private int HIGHSCORE = 0;
    private CustomImage currentPlayerSkin;

    //GameLoop object which regulate the drawing and the update calls
    private GameLoop gameLoop;

    //UserDao object
    private static UserDao userDao;

    //Gamescenes, including the currentScene which holds the currentScene beeing drawn
    private Scene currentScene;


    SongPlayer playlist;





    /**
     * Constructor of the gameEngine
     * @param context
     * Reference to the activity context
     */
    public Game(Context context) {
        super(context);



        //Create the surface holder instance to add all the callbacks (surfaceCreated, surfaceChanged, surfaceDestroyed, onTouchEvent, etc)
        //Allows to modify and update the pixel on the screen
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);



        //Database initialisation
        AppDatabase db;
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userDao = db.userDao();
        if (userDao.getAll().size() == 0) { User user = new User(0, 100); userDao.insert(user); }

        //!!!!GetPlayerSkin


    }


    /**
     * Callback that triggers when the surface (canvas) is created
     * @param surfaceHolder
     * Reference of the surfaceHolder than we have to use
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        

        //Setup the public static variable that represents the canvasSize of the phone
        Scene.setCanvasSize(this.getWidth(), this.getHeight());


        //Create the necessary scene
        currentScene = new MainMenuScene(getContext(), this, HIGHSCORE);

        playlist = new SongPlayer();
        playlist.addSong(getContext(), R.raw.menu_track);
        playlist.addSong(getContext(), R.raw.normal_track);
        playlist.addSong(getContext(), R.raw.highscore_track);

        playlist.play(0);

        //Create gameLoop and start it
        gameLoop = new GameLoop(this, surfaceHolder);
        gameLoop.startLoop();

    }

    /**
     * Callback function that triggers when there is a change in the surface created
     * @param surfaceHolder
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //currentScene.cleanup();
        //playlist.cleanup();
    }

    /**
     * Callback function that triggers when the current surface is destroyed
     * @param surfaceHolder
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        currentScene.cleanup();
        playlist.cleanup();
    }

    /**
     * Callback function that triggers when there is a touchEvent on the screen
     * @param event
     * Reference to the event that triggered the function
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        currentScene.onTouchEvent(event);
        return true;
    }


    /**
     * Callback function that triggers to update the canvas (pixel array of the screen)
     * @param canvas
     * Reference to the screen canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Draw the currentScene
        currentScene.draw(canvas);

    }

    /**
     * Updates the state of the currentScene
     */
    public void update()
    {
        //Update the currentScene
        currentScene.update();
    }

    /**
     * Change the currentScene based on an index
     * @param scene
     * Index of the next scene selected
     */
    public void switchScene(int scene)
    {
        switch(scene)
        {
            case 0 :

                try {



                    playlist.reset();
                    playlist.play(0);
                    //currentScene.cleanup();
                    currentScene = new MainMenuScene(getContext(), this, HIGHSCORE);
                }
                catch (OutOfMemoryError e)
                {
                   //Missing memory
                    System.out.println(e);
                }
                return;

            case 1 :
                //Teste la possibilite de memoire insuffisante de l'application
                try {
                    playlist.reset();
                    playlist.play(1);
                    //currentScene.cleanup();
                    currentScene = new GameScene(getContext(), this, new CustomImage(currentPlayerSkin));
                }
                catch (OutOfMemoryError e)
                {
                    //Missing memory
                    System.out.println(e);

                }
                return;
            case 2:
                //Teste la possibilite de memoire insuffisante de l'application
                try {
                    CustomiseScene c1 = new CustomiseScene(getContext(), this);
                    c1.updateOnce();
                    //currentScene.cleanup();
                    currentScene = c1;
                }
                catch (OutOfMemoryError e)
                {
                    //Missing memory
                    System.out.println(e);
                }

                return;
            default:
                //Teste la possibilite de memoire insuffisante de l'application
                try {
                    //currentScene.cleanup();
                    currentScene = new SettingScene(getContext(), this, SOUNDVALUE);
                }
                catch (OutOfMemoryError e)
                {
                    System.out.println(e);
                    //Missing memory

                }

                return;
        }


    }

    /**
     * Update the value of the globalSoundValue
     * @param soundValue
     * New sound value
     */
    public void setSoundValue(int soundValue) {
        SOUNDVALUE = soundValue;
    }

    /**
     * Add coin to the GAME COIN counter
     */
    public  void addCoin()
    {
        COINS++;
    }

    /**
     * Return the COIN counter
     * @return
     */
    public  int getCoins() {return COINS;}

    /**
     * Reduce the COIN counter by a certain amount
     * @param amount
     * Amount to reduce the COIN counter by
     */
    public void useCoin(int amount)
    {
        COINS  = Math.max(COINS-amount, 0);
    }

    /**
     * Set the player's skin
     * @param skin
     * CustomImage of the current skin of the player
     */
    public void setPlayerSkin(CustomImage skin)
    {
        currentPlayerSkin = skin;
    }

    /**
     * Get the Average UPS
     * @return
     * Average UPS
     */
    public double getAverageUPS()
    {
        return gameLoop.getAverageUPS();
    }

    public static UserDao database() {return userDao;}

}

