package com.example.joshrubin.testgame;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static com.example.joshrubin.testgame.GameView.hit;

public class MainActivity extends Activity {

    public static boolean pause = false;
    public static boolean start = false;
    public static boolean restart = false;
    public static boolean stats = false;
    private LinearLayout statsLayout;
    private FrameLayout layoutF;
    private LayoutInflater inflate;
    private RelativeLayout pauseLayout;
    public static RelativeLayout button;
    private RelativeLayout mainmenu;
    private RelativeLayout gameOverLayout;
    public static int score;
    private Button mode;
    private MediaPlayer mySound;
    private boolean soundOn = false;
    private int paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        GameView gameView = new GameView(this);
        setContentView(R.layout.activity_main);
        mode = (Button) findViewById(R.id.mode_button);

        layoutF = (FrameLayout) findViewById(R.id.Frame);
        inflate = getLayoutInflater();

        View pausebutton = inflate.inflate(R.layout.button_layout, layoutF, false);
        button = (RelativeLayout) pausebutton.findViewById(R.id.relative_layout);

        View pausing = inflate.inflate(R.layout.pause_layout, layoutF, false);
        pauseLayout = (RelativeLayout) pausing.findViewById(R.id.pause_layout);

        View menu = inflate.inflate(R.layout.main_menu, layoutF, false);
        mainmenu = (RelativeLayout) menu.findViewById(R.id.menu_id);

        View stats = inflate.inflate(R.layout.stats_layout, layoutF, false);
        statsLayout = (LinearLayout) stats.findViewById(R.id.stats_id);

        layoutF.addView(gameView);
        layoutF.addView(mainmenu);

        mySound = MediaPlayer.create(this,R.raw.patakas_world);
        mySound.start();
        mySound.setLooping(true);

    }

    public void pause(View view) {
        if (!hit) {
            pause = true;
            layoutF.removeView(button);
            layoutF.addView(pauseLayout);
        }
    }

    public void resume(View view) {
        pause = false;
        layoutF.removeView(pauseLayout);
        layoutF.addView(button);
    }

    public void start(View view) {
        start = true;
        layoutF.removeView(mainmenu);
        layoutF.addView(button);

    }

    public void quit(View view) {
        start = false;
        pause = false;
        GameView.gameUpdate = false;
        GameView.obstacle1 = null;
        GameView.obstacle2 = null;
        GameView.obstacle3 = null;
        GameView.obstacle4 = null;
        GameView.doodle = null;
        layoutF.removeView(pauseLayout);
        layoutF.addView(mainmenu);

    }

    public void restart(View view) {
        pause = false;
        start = true;
        restart = true;
        layoutF.removeView(pauseLayout);
        layoutF.addView(button);

    }


    public void gameOver(View view) {

        layoutF.removeView(pauseLayout);
        layoutF.addView(gameOverLayout);

    }

    public void switchMode(View view) {
        if (GameView.gameMode == 0) {
            GameView.gameMode = 1;
        } else if (GameView.gameMode == 1) {
            GameView.gameMode = 2;
        } else if (GameView.gameMode == 2) {
            GameView.gameMode = 0;
        }
    }

    public void showStats(View view) {
        layoutF.removeView(mainmenu);
        layoutF.addView(statsLayout);
        stats = true;
    }

    public void goBack(View view) {
        layoutF.addView(mainmenu);
        layoutF.removeView(statsLayout);
        stats = false;
    }

    public void musicToggle(View view) {
        if (!soundOn) {
            mySound.pause();
            paused = mySound.getCurrentPosition();
            soundOn = true;
        } else {
            mySound.seekTo(paused);
            mySound.start();
            soundOn = false;
        }
    }


}
