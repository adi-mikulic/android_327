package com.example.joshrubin.testgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by joshrubin on 12/5/17.
 */

public class Obstacle {

    public Bitmap image;
    public static double scale = .5;
    public int x;
    public int y;
    public int sizeX, sizeY;
    private double speed = 3.9 * Background.scale;
    public boolean pointcheck;
    public boolean other;
    public Obstacle pair;

    public int getRand() {
        int rand = new Random().nextInt(70);
        return GameView.place[rand];
    }

    public Obstacle(Bitmap image, int x, int y, boolean other, Obstacle pair) {
        sizeX = (int) (287 * Background.scale * .3);
        sizeY = (int) (1758 * Background.scale * .3);
        Bitmap nImage = Bitmap.createScaledBitmap(image, sizeX, sizeY, true);
        this.image = nImage;
        this.x = x;
        this.y = y;
        this.other = other;
        this.pair = pair;
        pointcheck = true;
    }

    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, x, y, null);
    }

    public void update() {
        if (GameView.gameMode!=0){
            speed += .0055 * Background.scale;
        } else {
            speed += .0027 * Background.scale;
        }
        x -= (speed);
        if (x < -GameView.S_WIDTH) {
            x = GameView.S_WIDTH;
            if (!other) {
                y = getRand();
            } else {
                y = GameView.getOther(pair);
            }
        }
        if (x == GameView.S_WIDTH) {
            pointcheck = true;
        }
    }

}
