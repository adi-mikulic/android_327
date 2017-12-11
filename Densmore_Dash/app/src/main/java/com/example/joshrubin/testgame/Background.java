package com.example.joshrubin.testgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by joshrubin on 12/5/17.
 */

public class Background {

    //object variables
    private Bitmap background;
    private int x;
    private int y;
    private float speed;
    private static final double B_HEIGHT = 600;
    private static final double B_WIDTH = 1600;
    public static double scale = GameView.S_HEIGHT / B_HEIGHT;
    private static int width = (int) (scale * B_WIDTH);

    //background constructor
    public Background(Bitmap background) {

        //set up variables
        double scale = GameView.S_HEIGHT / B_HEIGHT;
        int width = (int) (scale * B_WIDTH);
        this.background = Bitmap.createScaledBitmap(background, width, GameView.S_HEIGHT, true);

    }

    //draw function
    public void draw(Canvas canvas) {
        canvas.drawBitmap(background, x, y, null);

        //if all the way off the screen, draw another bitmap right behing it
        if (x < 0) {
            canvas.drawBitmap(background, (int) (x + width), y, null);
        }
    }

    //update function
    public void update() {

        //move background across screen
        if (MainActivity.start && GameView.gameMode != 0) {

            //speeds up on start
            speed += .0055 * Background.scale;
            x -= speed;
        } else {

            //slow and steady on mainmenu
            speed = (float) (2.8 * Background.scale);
            x -= speed;
        }

        //if if off screen by width, move to x=0
        if (x < -width) {
            x = 0;
        }
    }

}
