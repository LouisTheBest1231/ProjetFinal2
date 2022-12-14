package com.example.projetfinal;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop  extends Thread{
    private static final double MAX_UPS = 60;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;

    static private double averageFPS;
    static private double averageUPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }



    public static double getAverageUPS() {
        return averageUPS;
    }

    public static double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }


    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;


        startTime = System.currentTimeMillis();

        Canvas canvas = null;
        while(isRunning)
        {


            try
            {
                canvas = surfaceHolder.lockCanvas();

                if(canvas == null)
                    return;
                synchronized (surfaceHolder)
                {
                    game.update();
                    updateCount++;

                    game.draw(canvas);
                }


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
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }




            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime =(long) (updateCount *UPS_PERIOD - elapsedTime);

            if(sleepTime > 0)
            {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            while(sleepTime < 0 && updateCount < MAX_UPS-1)
            {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis()-startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }

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

