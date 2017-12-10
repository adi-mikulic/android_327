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

    private Bitmap background;
    private int x;
    private int y;
    private float speed;
    private static final double B_HEIGHT = 600;
    private static final double B_WIDTH = 1600;
    public static double scale = GameView.S_HEIGHT/B_HEIGHT;
    private static int width = (int) (scale*B_WIDTH);

    public Background(Bitmap background) {
        double scale = GameView.S_HEIGHT/B_HEIGHT;
        int width = (int) (scale*B_WIDTH);
        this.background= Bitmap.createScaledBitmap(background,width,GameView.S_HEIGHT, true);

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(background,x,y,null);

        if (x<0) {
            canvas.drawBitmap(background,(int)(x+width),y,null);
        }
    }

    public void update() {
        if (MainActivity.start && GameView.gameMode!=0) {
            speed+=.0055*Background.scale;
            x-=speed;
        } else {
            speed = (float)(2.8*Background.scale);
            x-=speed;
        }
        if (x<-width) {
            x=0;
        }
    }

}
