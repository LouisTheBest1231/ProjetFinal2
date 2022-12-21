package com.example.projetfinal;


import android.graphics.Canvas;
import android.view.SurfaceHolder;


/**
 * GameLoop object which encapsulate the while loop for update and draw of the gameEngine,
 * Extends from the Thread class
 * @source This part of the code is mostly from this tutorial : https://www.youtube.com/playlist?list=PL2EfDMM6n_LYJdzaOQ5jZZ3Dj5L4tbAuM, a really good starting point to understand the basics of realtime rendering
 */
public class GameLoop  extends Thread{

    //static Const
    private final double MAX_UPS = 60;
    private final double UPS_PERIOD = 1E+3/MAX_UPS;

    //S
    private double averageFPS;
    private double averageUPS;


    //Condition for the loop to run
    private boolean isRunning = false;

    //References to the surfaceHolder, and to the game
    private SurfaceHolder surfaceHolder;
    private Game game;


    /**
     * Constructor of the gameloop
     * @param game
     * Reference to the parent GameObject
     * @param surfaceHolder
     * Reference to the surface Holder
     */
    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }


    /**
     * Get the Average update per second
     * @return
     * Return the average UPS
     */
    public double getAverageUPS() {
        return averageUPS;
    }



    /**
     * Starts the gameLoop
     */
    public void startLoop() {
        isRunning = true;
        start();
    }


    /**
     * Callback to the run function of the Thread, starts the MainLoop
     */
    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        //Get the startTime to measure the elapsed time
        startTime = System.currentTimeMillis();


        Canvas canvas = null;



        //Main gameLoop Loop
        while(isRunning)
        {


            try
            {
                //Get the canvas ref
                canvas = surfaceHolder.lockCanvas();

                if(canvas == null)
                    return;

                //Update, and draw
                game.update();
                updateCount++;
                game.draw(canvas);




            }
            catch(IllegalArgumentException a)
            {
                a.printStackTrace();
            }
            finally
            {
                if(canvas != null)
                {
                    try
                    {
                        //Unlock the surface
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }



            //Get the elapsed time and the sleepTime necessary to reach Frame/second goal
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime =(long) (updateCount *UPS_PERIOD - elapsedTime);

            //Sleep for specified amount
            if(sleepTime > 0)
            {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            //Multiple update calls in order to reach the number specified (MAX_UPS)
            while(sleepTime < 0 && updateCount < MAX_UPS-1)
            {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis()-startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }

            //
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000)
            {
                averageUPS = updateCount/(1E-3* elapsedTime);
                averageFPS = frameCount/(1E-3*elapsedTime);

                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }

        }
    }
}

