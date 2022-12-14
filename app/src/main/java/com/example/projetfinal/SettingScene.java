package com.example.projetfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class SettingScene extends Scene{

    CustomButton back;
    CustomButton left;
    CustomButton right;

    CustomImage son;


    Game pointerGame;


    public SettingScene(Context context, Game gameRef, int SOUNDVALUE)
    {

        pointerGame = gameRef;

        son= new CustomImage(R.drawable.soundspritesheet, 5, 3, 6,                    Scene.canvasSize.getX()/2, Scene.canvasSize.getY()/2,600,400, context);
        son.setAnimateurState(SOUNDVALUE);

        back = new CustomButton(R.drawable.backspritesheet, 5,3,3,220,Scene.canvasSize.getY()-120,400,200, context);
        left = new CustomButton(R.drawable.flechegauchespritesheet, 5,3,3, Scene.canvasSize.getX()/2 - 400, Scene.canvasSize.getY()/2, 200,200,context);
        right = new CustomButton(R.drawable.flechedroitespritesheet, 5,3,3, Scene.canvasSize.getX()/2 + 400, Scene.canvasSize.getY()/2, 200,200,context);



        back.setButtonAction(back.new ButtonAction() {
            @Override
            public void onClick() {
                pointerGame.switchScene(0);
            }
        });

        left.setButtonAction(left.new ButtonAction() {
            @Override
            public void onClick() {
                int soundValue = ((son.getAnimateurState() - 1)%6 + 6)%6;
                son.setAnimateurState(  soundValue);
                pointerGame.setSoundValue(soundValue);

                // - sound
            }
        });

        right.setButtonAction(right.new ButtonAction() {
            @Override
            public void onClick() {
                int soundValue = ((son.getAnimateurState() + 1)%6 + 6)%6;
                son.setAnimateurState(soundValue);
                pointerGame.setSoundValue(soundValue);
                // + sound
            }
        });

    }

    @Override
    public void onTouchEvent(MotionEvent e) {
        back.onTouchEvent(e);
        left.onTouchEvent(e);
        right.onTouchEvent(e);
    }

    @Override
    public void draw(Canvas canvas) {
        son.draw(canvas);
        left.draw(canvas);
        right.draw(canvas);
        back.draw(canvas);

    }

    @Override
    public void update() {
        son.update();
        left.update();
        right.update();
        back.update();


    }
}
