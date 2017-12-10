package com.example.joshrubin.testgame;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.widget.TextView;

import com.example.joshrubin.testgame.MainActivity;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }

    void setRunning(boolean isRunning) {
        running = isRunning;
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    @Override
    public void run() {

        while (running) {
            canvas = null;
            try {
                while (MainActivity.pause /*|| GameView.hit*/) {
                    if (MainActivity.restart) {
                        break;
                    }

                    sleep(1);
                }
            } catch (Exception exception) {

            }
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);

                }
            } catch (Exception exception) {

            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
    }
}


