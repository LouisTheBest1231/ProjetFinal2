package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Encapsulate the spawning and the maintenance of the ennemies objects
 */
public class Spawner {

    private ArrayList<Enemy> enemies;

    /**
     * Creates the ennemies ArrayList
     */
    public Spawner()
    {
        enemies = new ArrayList<Enemy>();
    }

    /**
     * Get the amount of enemies existing
     * @return
     * Amount of enemies
     */
    public int getEnemiesSize()
    {
        return enemies.size();
    }

    /**
     * Draw all the enemies
     */
    public void draw(Canvas canvas)
    {
        for(int i =0; i < enemies.size(); i++)
        {
            enemies.get(i).draw(canvas);
        }
    }

    /**
     * Update the enemies position and test collisions
     * @param pointerGame
     * Reference to the GAME object
     * @param playerPosition
     * Position of the player to testCollision
     * @param playerSize
     * Size of the player (r)
     * @return
     */
    public boolean update( Game pointerGame, Vector playerPosition, float playerSize)
    {
        for(int i =enemies.size()-1; i>= 0; i--)
        {
            //Update enemies
            enemies.get(i).update(pointerGame.getAverageUPS() / 1000);

            //Test collisions with player
            if(enemies.get(i).testCollision(playerPosition,  playerSize))
            {
                return true;

            }
            //Test if the ennemies are still visible
            if(enemies.get(i).outOfBounds())
            {
                enemies.remove(i);
            }
        }
        return false;
    }


    /**
     * Add an enemy to the game
     * @param stageLevel
     * Level of difficulty of the Enemy
     * @param context
     * Reference to the context of the Activity
     */
    public void addEnemy(int stageLevel, Context context)
    {
        enemies.add(spawn(stageLevel, context));
    }


    //Returns a variaty of ennemies based on the difficulty level
    private Enemy spawn(int stageLevel, Context context)
    {
        Random r = new Random();
        if(stageLevel == 0)
        {
            int sizeX = r.nextInt(100) + 100;
            int sizeY = r.nextInt(100) + 100;
            int speed = r.nextInt(20) + 20;
            return new EnemyStraight(context, new Vector(sizeX,sizeY), speed);
        }
        else if(stageLevel == 1 || stageLevel ==2)
        {
            int select = r.nextInt(2);
            if(select == 0)
            {
                int sizeX = r.nextInt(100 + (stageLevel*5)) + 100;
                int sizeY = r.nextInt(100 + (stageLevel*5)) + 100;
                int speed = r.nextInt(20+(stageLevel*5)) + 20;
                return new EnemyStraight(context, new Vector(sizeX,sizeY), speed);

            }
            else
            {
                int sizeX = r.nextInt(20 + (stageLevel*5)) + 30;
                int sizeY = r.nextInt(20 + (stageLevel*5)) + 30;
                int speed = r.nextInt(20+(stageLevel*5)) + 5;
                float amplitude = r.nextInt(9000) + 3000;
                return new EnemyCurve(context, new Vector(sizeX,sizeY), speed,amplitude);

            }
        }
        else if(stageLevel == 3 || stageLevel ==4)
        {
            int select = r.nextInt(2);
            if(select == 0)
            {
                int sizeX = r.nextInt(100 + (stageLevel*10)) + 100;
                int sizeY = r.nextInt(100 + (stageLevel*10)) + 100;
                int speed = r.nextInt(20+(stageLevel*10)) + 20;
                return new EnemyStraight(context, new Vector(sizeX,sizeY), speed);

            }
            else
            {
                int sizeX = r.nextInt(20 + (stageLevel*10)) + 40;
                int sizeY = r.nextInt(20 + (stageLevel*10)) + 40;
                int speed = r.nextInt(20+(stageLevel*10)) + 10;
                float amplitude = r.nextInt(3000) + 3000;
                return new EnemyCurve(context, new Vector(sizeX,sizeY), speed,amplitude);

            }
        }
        else if(stageLevel == 5 || stageLevel == 6)
        {
            int sizeX = r.nextInt(30 + (stageLevel*15)) + 20;
            int sizeY = r.nextInt(30 + (stageLevel*15)) + 20;
            int speed = r.nextInt(20 + (stageLevel)) + 3;
            float amplitude = r.nextInt(200) + 200;
            float periode = r.nextInt(10 * stageLevel) + 35;
            return new EnemySin(context, new Vector(sizeX,sizeY), speed,periode,amplitude);
        }
        else if(stageLevel == 7 || stageLevel == 8)
        {
            int select = r.nextInt(2);
            if(select == 0)
            {
                int sizeX = r.nextInt(100 + (stageLevel*15)) + 100;
                int sizeY = r.nextInt(100 + (stageLevel*15)) + 100;
                int speed = r.nextInt(20+(stageLevel*2)) + 20;
                return new EnemyStraight(context, new Vector(sizeX,sizeY), speed);

            }
            else
            {
                int sizeX = r.nextInt(30 + (stageLevel*15)) + 20;
                int sizeY = r.nextInt(30 + (stageLevel*15)) + 20;
                int speed = r.nextInt(20 + (stageLevel)) + 3;
                float amplitude = r.nextInt(200) + 200;
                float periode = r.nextInt(10 * stageLevel) + 35;
                return new EnemySin(context, new Vector(sizeX,sizeY), speed,periode,amplitude);

            }
        }
        else if(stageLevel == 9 || stageLevel == 10)
        {
            int select = r.nextInt(2);
            if(select == 0)
            {
                int sizeX = r.nextInt(20 + (stageLevel*10)) + 40;
                int sizeY = r.nextInt(20 + (stageLevel*10)) + 40;
                int speed = r.nextInt(20+(stageLevel*2)) + 10;
                float amplitude = r.nextInt(stageLevel * 500) + 3000;
                return new EnemyCurve(context, new Vector(sizeX,sizeY), speed,amplitude);

            }
            else
            {
                int sizeX = r.nextInt(30 + (stageLevel*15)) + 20;
                int sizeY = r.nextInt(30 + (stageLevel*15)) + 20;
                int speed = r.nextInt(20 + (stageLevel)) + 3;
                float amplitude = r.nextInt(200) + 200;
                float periode = r.nextInt(10 * stageLevel) + 35;
                return new EnemySin(context, new Vector(sizeX,sizeY), speed,periode,amplitude);

            }
        }
        else
        {

            int select = r.nextInt(3);
            if(select == 0)
            {
                int sizeX = r.nextInt(100 + (stageLevel*15)) + 100;
                int sizeY = r.nextInt(100 + (stageLevel*15)) + 100;
                int speed = r.nextInt(20+(stageLevel*2)) + 20;
                return new EnemyStraight(context, new Vector(sizeX,sizeY), speed);
            }
            else if(select == 1)
            {
                int sizeX = r.nextInt(20 + (stageLevel*10)) + 40;
                int sizeY = r.nextInt(20 + (stageLevel*10)) + 40;
                int speed = r.nextInt(20+(stageLevel*2)) + 10;
                float amplitude = r.nextInt(stageLevel * 500) + 3000;
                return new EnemyCurve(context, new Vector(sizeX,sizeY), speed,amplitude);

            }
            else
            {
                int sizeX = r.nextInt(30 + (stageLevel*15)) + 20;
                int sizeY = r.nextInt(30 + (stageLevel*15)) + 20;
                int speed = r.nextInt(20 + (stageLevel)) + 3;
                float amplitude = r.nextInt(200) + 200;
                float periode = r.nextInt(10 * stageLevel) + 35;
                return new EnemySin(context, new Vector(sizeX,sizeY), speed,periode,amplitude);

            }
        }
    }
}
