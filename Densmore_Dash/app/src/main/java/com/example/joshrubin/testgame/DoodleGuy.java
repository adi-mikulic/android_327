package com.example.joshrubin.testgame;


import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by joshrubin on 12/4/17.
 */

public class DoodleGuy {
    public Bitmap image;
    private boolean jump;
    public static float x;
    public float y;
    private float yChange;
    private double force;
    public int sizeX, sizeY;


    public DoodleGuy(Bitmap image) {
        sizeX = (int) (111 * Background.scale);
        sizeY = (int) (111 * Background.scale);
        Bitmap nImage = Bitmap.createScaledBitmap(image, sizeX, sizeY, true);
        this.image = nImage;
    }

    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, x, y, null);
    }

    public void goUp(boolean jump) {
        this.jump = jump;
    }

    public void update() {

        if (jump) {
            yChange+= force;
        } else {
            yChange -= force;
        }
        y -= yChange;
        if (y - yChange < 0) {
            y = 0;
            yChange = 0;
        }
        if (y - yChange > GameView.S_HEIGHT - image.getHeight()) {
            y = GameView.S_HEIGHT - image.getHeight();
            yChange = 0;
        }
    }

    public void setStart(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public void setForce(double force) {
        this.force=force;
    }


}
