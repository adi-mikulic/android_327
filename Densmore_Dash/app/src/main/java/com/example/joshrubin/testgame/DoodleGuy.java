package com.example.joshrubin.testgame;


import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by joshrubin on 12/4/17.
 */

public class DoodleGuy {

    //object variables
    public Bitmap image;
    private boolean jump;
    public static float x;
    public float y;
    private float yChange;
    private double force;
    public int sizeX, sizeY;

    //DoodleGuy constructor
    public DoodleGuy(Bitmap image) {

        //setup size
        sizeX = (int) (111 * Background.scale);
        sizeY = (int) (111 * Background.scale);

        //make bitmap
        Bitmap nImage = Bitmap.createScaledBitmap(image, sizeX, sizeY, true);
        this.image = nImage;
    }

    //draw function
    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, x, y, null);
    }

    //change jump function
    public void goUp(boolean jump) {
        this.jump = jump;
    }

    //update function
    public void update() {

        //works similar to gravity

        //if jump is true make character go up
        if (jump) {
            yChange += force;
        } else {

            //else go down
            yChange -= force;
        }
        y -= yChange;

        //for when hitting screen bounds
        if (y - yChange < 0) {
            y = 0;
            yChange = 0;
        }
        if (y - yChange > GameView.S_HEIGHT - image.getHeight()) {
            y = GameView.S_HEIGHT - image.getHeight();
            yChange = 0;
        }
    }

    //set start place
    public void setStart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //set force value
    public void setForce(double force) {
        this.force = force;
    }

}
