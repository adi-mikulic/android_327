package com.example.joshrubin.testgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by joshrubin on 12/5/17.
 */

public class Obstacle {

    //object variables
    public Bitmap image;
    public static double scale = .5;
    public int x;
    public int y;
    public int sizeX, sizeY;
    private double speed = 3.9 * Background.scale;
    public boolean pointcheck;
    public boolean other;
    public Obstacle pair;

    //object constructor
    public Obstacle(Bitmap image, int x, int y, boolean other, Obstacle pair) {

        //set up variables
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

    //draw function
    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, x, y, null);
    }

    //update function
    public void update() {

        //update based on gamemode
        if (GameView.gameMode!=0){
            speed += .0055 * Background.scale;
        } else {
            speed += .0027 * Background.scale;
        }
        x -= (speed);

        //if off screen by screen width, go back to end of screen
        if (x < -GameView.S_WIDTH) {
            x = GameView.S_WIDTH;

            //set up other if on gamemode hard
            if (!other) {
                y = getRand();
            } else {
                y = GameView.getOther(pair);
            }
        }

        //for point check
        if (x == GameView.S_WIDTH) {
            pointcheck = true;
        }
    }

    //get random number from array function
    public int getRand() {
        int rand = new Random().nextInt(70);
        return GameView.place[rand];
    }

}
