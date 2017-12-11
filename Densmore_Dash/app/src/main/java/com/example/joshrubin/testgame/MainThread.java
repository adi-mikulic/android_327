package com.example.joshrubin.testgame;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.widget.TextView;

import com.example.joshrubin.testgame.MainActivity;

public class MainThread extends Thread {

    //object variables
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {

        //cals base class constructor and set up gameView and surfaceHolder
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }

    //set running to true
    void setRunning(boolean isRunning) {
        running = isRunning;
    }

    //overridden function that calls the base class function
    @Override
    public void interrupt() {
        super.interrupt();
    }

    //overridden function that runs the thread
    @Override
    public void run() {

        //for when the thread is running
        while (running) {
            canvas = null;
            try {
                //if game is paused, pause the thread
                while (MainActivity.pause /*|| GameView.hit*/) {
                    if (MainActivity.restart) {
                        break;
                    }

                    sleep(1);
                }
            } catch (Exception exception) {

            }
            try {

                //lock canvas to paint on it
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);

                }
            } catch (Exception exception) {

            } finally {
                if (canvas != null) {
                    try {

                        //unlock canvas and display it
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
    }
}


