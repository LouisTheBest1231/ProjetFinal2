package com.example.projetfinal.Scene;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.projetfinal.DrawingUtilityClass.CustomButton;
import com.example.projetfinal.DrawingUtilityClass.CustomImage;
import com.example.projetfinal.Game;
import com.example.projetfinal.R;
import com.example.projetfinal.User;

/**
 * Child of SCENE - SettingScene - Encapsulate all the functionalities of the SETTINGSCENE
 */
public class SettingScene extends Scene {

    CustomButton back;
    CustomButton left;
    CustomButton right;

    CustomImage son;


    Game pointerGame;


    MediaPlayer mediaPlayerButton;

    /**
     * Constructor that initialize all the settings of SettingScene
     * @param context
     * Reference to the context of the Activity
     * @param gameRef
     * Reference to the GAME object
     * @param SOUNDVALUE
     * Initial soundValue
     */
    public SettingScene(Context context, Game gameRef, int SOUNDVALUE)
    {

        mediaPlayerButton = MediaPlayer.create(context, R.raw.press_sound2);

        //Set GAME object pointer
        pointerGame = gameRef;


        //Set up all the CustomImage and CustomButton
        son= new CustomImage(R.drawable.soundspritesheet, 5, 3, 6,                    Scene.getCanvas().getX()/2, Scene.getCanvas().getY()/2,600,400, context);
        son.setAnimateurState(SOUNDVALUE);
        back = new CustomButton(R.drawable.backspritesheet, 5,3,3,220,Scene.getCanvas().getY()-120,400,200, context);
        left = new CustomButton(R.drawable.flechegauchespritesheet, 5,3,3, Scene.getCanvas().getX()/2 - 400, Scene.getCanvas().getY()/2, 200,200,context);
        right = new CustomButton(R.drawable.flechedroitespritesheet, 5,3,3, Scene.getCanvas().getX()/2 + 400, Scene.getCanvas().getY()/2, 200,200,context);


        //Set up all the ButtonAction of the CustomButton
        back.setButtonAction(back.new ButtonAction() {
            @Override
            public void onClick() {
                pointerGame.switchScene(0);
            }
        });

        //Sound--
        left.setButtonAction(left.new ButtonAction() {
            @Override
            public void onClick() {
                //Change the sprite and change the soundVALUE in the GAME object
                int soundValue = Math.max(0, son.getAnimateurState()-1);
                son.setAnimateurState(  soundValue);
                pointerGame.setSoundValue(soundValue);

                mediaPlayerButton.start();

                //Modify the database around new value
                User tempUser = Game.database().findById(1);
                if (tempUser.getSound() > 0) { tempUser.setSound(tempUser.getSound() - 20); }
                else { tempUser.setSound(100); }
                Game.database().delete(Game.database().findById(1));
                Game.database().insert(tempUser);

            }
        });

        //Sound++
        right.setButtonAction(right.new ButtonAction() {
            @Override
            public void onClick() {
                //Change the sprite and change the soundVALUE in the GAME object
                int soundValue = Math.min(5, son.getAnimateurState()+1);
                son.setAnimateurState(soundValue);
                pointerGame.setSoundValue(soundValue);

                mediaPlayerButton.start();

                //Modify the database around new value
                User tempUser = Game.database().findById(1);// + sound
                if (tempUser.getSound() < 100) { tempUser.setSound(tempUser.getSound() + 20); }
                else { tempUser.setSound(0); }
                Game.database().delete(Game.database().findById(1));
                Game.database().insert(tempUser);
            }
        });

    }

    /**
     * Update the buttons based on the user's input
     * @param e
     * MotionEvent of the USER
     */
    @Override
    public void onTouchEvent(MotionEvent e) {
        back.onTouchEvent(e);
        left.onTouchEvent(e);
        right.onTouchEvent(e);
    }

    /**
     * Drawing all the objects
     * @param canvas
     * Reference to the CANVAS
     */
    @Override
    public void draw(Canvas canvas) {
        son.draw(canvas);
        left.draw(canvas);
        right.draw(canvas);
        back.draw(canvas);

    }

    /**
     * Update all the animated Objects
     */
    @Override
    public void update() {
        son.update();
        left.update();
        right.update();
        back.update();


    }


    /**
     * Cleanup and remove from heap
     */
    @Override
    public void cleanup() {
        mediaPlayerButton.release();

    }
}
