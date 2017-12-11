package com.example.joshrubin.testgame;

import android.content.SharedPreferences;
import android.view.SurfaceView;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.text.DecimalFormat;

import java.util.Random;


/**
 * Created by joshrubin on 12/4/17.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    //object variables
    private Background background;
    public static MainThread thread;
    public static DoodleGuy doodle;
    public static Obstacle obstacle1;
    public static Obstacle obstacle2;
    public static Obstacle obstacle3;
    public static Obstacle obstacle4;
    public static Obstacle obstacle5;
    public static Obstacle obstacle6;
    public static Obstacle obstacle7;
    public static Obstacle obstacle8;
    public static boolean hit;
    public static boolean gameUpdate = false;
    public static Obstacle[] obstacleArray;
    public static float points = 0;
    public final static int S_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    public final static int S_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static int[] place = new int[70];
    private int count = 0;
    public static int gameMode = 0;
    private Paint mode = new Paint();
    private Paint displayHS = new Paint();
    private Paint displayHSO = new Paint();
    private Paint paintScore = new Paint();
    private Paint highScore = new Paint();
    private Paint gameOver = new Paint();
    private Paint outline = new Paint();
    private Paint endScore = new Paint();
    private Paint touchRestart = new Paint();
    private Paint stats = new Paint();
    SharedPreferences settings1;
    SharedPreferences settings2;
    SharedPreferences settings3;
    SharedPreferences settings4;
    SharedPreferences settings5;
    SharedPreferences settings6;
    SharedPreferences settings7;
    SharedPreferences.Editor editor1;
    SharedPreferences.Editor editor2;
    SharedPreferences.Editor editor3;
    SharedPreferences.Editor editor4;
    SharedPreferences.Editor editor5;
    SharedPreferences.Editor editor6;
    SharedPreferences.Editor editor7;
    public static final String fileNameOS = "overallHighScore";
    public static final String fileNameES = "easyHighScore";
    public static final String fileNameMS = "mediumHighScore";
    public static final String fileNameHS = "hardHighScore";
    public static final String fileNameGP = "gamesPlayed";
    public static final String fileNameAS = "averageScore";
    public static final String fileNameTP = "totalPoints";
    public static int saveOverallHighScore;
    public static int hardHighScore;
    public static int mediumHighScore;
    public static int easyHighScore;
    public static int gamesPlayed;
    public static int totalPoints;
    public static float averageScore;
    private boolean newHighScore;
    private int countHS = 0;
    private int countDensmore = 0;

    public GameView(Context context) {
        //extends bas class constructor
        super(context);

        //sets up surface holder for the GameView
        getHolder().addCallback(this);

        //sets up constructor variables
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        settings1 = getContext().getSharedPreferences(fileNameOS, 0);
        settings2 = getContext().getSharedPreferences(fileNameES, 0);
        settings3 = getContext().getSharedPreferences(fileNameMS, 0);
        settings4 = getContext().getSharedPreferences(fileNameHS, 0);
        settings5 = getContext().getSharedPreferences(fileNameGP, 0);
        settings6 = getContext().getSharedPreferences(fileNameAS, 0);
        settings7 = getContext().getSharedPreferences(fileNameTP, 0);
        editor1 = settings1.edit();
        editor2 = settings2.edit();
        editor3 = settings3.edit();
        editor4 = settings4.edit();
        editor5 = settings5.edit();
        editor6 = settings6.edit();
        editor7 = settings7.edit();
    }

    public void setArrays() {
        //sets up array with various obstacle heights
        for (int i = -(S_HEIGHT / 10), j = 0; j < 35; i -= 5.6 * Background.scale, j++) {
            place[j] = i;
        }
        for (int i = (int) (GameView.S_HEIGHT - 1758 * Background.scale * .25), j = 35; j < 70; i += 5.6 * Background.scale, j++) {
            place[j] = i;
        }
    }

    public int getRand() {
        //get random number for array above
        int rand = new Random().nextInt(70);
        return place[rand];
    }

    //overridden function
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    //overridden function
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //establishes saved data from phone, creates it if not there, assigns to variables if already exists
        if (!settings1.contains("overallHighScore")) {
            editor1.putInt("overallHighScore", 0);
            editor1.commit();
        } else {
            saveOverallHighScore = settings1.getInt("overallHighScore", 0);
        }
        if (!settings2.contains("easyHighScore")) {
            editor2.putInt("easyHighScore", 0);
            editor2.commit();
        } else {
            easyHighScore = settings2.getInt("easyHighScore", 0);
        }
        if (!settings3.contains("mediumHighScore")) {
            editor3.putInt("mediumHighScore", 0);
            editor3.commit();
        } else {
            mediumHighScore = settings3.getInt("mediumHighScore", 0);
        }
        if (!settings4.contains("hardHighScore")) {
            editor4.putInt("hardHighScore", 0);
            editor4.commit();
        } else {
            hardHighScore = settings4.getInt("hardHighScore", 0);
        }
        if (!settings5.contains("gamesPlayed")) {
            editor5.putInt("gamesPlayed", 0);
            editor5.commit();
        } else {
            gamesPlayed = settings5.getInt("gamesPlayed", 0);
        }
        if (!settings6.contains("averageScore")) {
            editor6.putFloat("averageScore", 0);
            editor6.commit();
        } else {
            averageScore = (float) settings6.getFloat("averageScore", 0);
        }
        if (!settings7.contains("totalPoints")) {
            editor7.putInt("totalPoints", 0);
            editor7.commit();
        } else {
            totalPoints = settings7.getInt("totalPoints", 0);
        }

        //set up array
        setArrays();

        //set up background object
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.scrolling_background));

        //start thread
        thread.setRunning(true);
        thread.start();

    }

    //overridden function
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //for when surface is destroyed
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                ;
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            retry = false;
        }
    }

    //update function
    public void update() {

        //update background if you did not hit an obstacle
        if (!hit) {
            background.update();
        } else {//increment count to not allow for restart game
            count++;
        }

        //for when game is playing
        if (gameUpdate && !hit) {

            //check is collision happened
            if (checkCollision(obstacleArray, doodle)) {

                //set hit to true and update game stats
                hit = true;
                totalPoints += points;
                editor7.putInt("totalPoints", (int) totalPoints);
                gamesPlayed++;
                editor5.putInt("gamesPlayed", (int) gamesPlayed);
                averageScore = (float) totalPoints / (float) gamesPlayed;
                editor6.putFloat("averageScore", averageScore);
                editor5.commit();
                editor6.commit();
                editor7.commit();
            }

            //update the player and obstacles
            doodle.update();
            obstacleUpdate(obstacleArray);

            //check if obstace passed doodle and if so add a point
            for (int i = 0; i < obstacleArray.length; i++) {
                if (obstacleArray[i].pointcheck && obstacleArray[i].x < doodle.x && obstacleArray[i].x > 0) {
                    points++;

                    //if in hard mode account for passing two obstacles at the same time
                    if (gameMode == 2) {
                        points -= .5;
                    }

                    //set point check back to false
                    obstacleArray[i].pointcheck = false;
                }
            }

            //update the game stats
            updateStats();
        }

        //for when you start or restart the game
        if (MainActivity.start || MainActivity.restart) {
            //set up player image based on if you equipped densmore or not
            if (MainActivity.makeDensmore) {
                doodle = new DoodleGuy(BitmapFactory.decodeResource(getResources(), R.drawable.densmore_player_sprite));
            } else {
                doodle = new DoodleGuy(BitmapFactory.decodeResource(getResources(), R.drawable.fox));
            }

            //set the starting place and force of the player
            doodle.setStart((int) (111 * Background.scale), (int) (111 * Background.scale));
            doodle.setForce(1 * Background.scale);

            //set up the obstacles
            obstacle1 = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.brick_pattern), (int) (1111 * Background.scale), getRand(), false, null);
            obstacle2 = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.brick_pattern), (int) (1111 * Background.scale) + (S_WIDTH / 2), getRand(), false, null);
            obstacle3 = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.brick_pattern), (int) (1111 * Background.scale) + (S_WIDTH), getRand(), false, null);
            obstacle4 = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.brick_pattern), (int) (1111 * Background.scale) + (S_WIDTH / 2 * 3), getRand(), false, null);

            //if game mode is not hard than there are only ever 4 obstacles
            if (gameMode != 2) {
                obstacleArray = new Obstacle[4];

            } else {

                //otherwise there are 8
                obstacleArray = new Obstacle[8];

                obstacle5 = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.brick_pattern), (int) (1111 * Background.scale), getOther(obstacle1), true, obstacle1);
                obstacle6 = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.brick_pattern), (int) (1111 * Background.scale) + (S_WIDTH / 2), getOther(obstacle2), true, obstacle2);
                obstacle7 = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.brick_pattern), (int) (1111 * Background.scale) + (S_WIDTH), getOther(obstacle3), true, obstacle3);
                obstacle8 = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.brick_pattern), (int) (1111 * Background.scale) + (S_WIDTH / 2 * 3), getOther(obstacle4), true, obstacle4);

                obstacleArray[4] = obstacle5;
                obstacleArray[5] = obstacle6;
                obstacleArray[6] = obstacle7;
                obstacleArray[7] = obstacle8;
            }
            obstacleArray[0] = obstacle1;
            obstacleArray[1] = obstacle2;
            obstacleArray[2] = obstacle3;
            obstacleArray[3] = obstacle4;

            //flip the start value
            if (MainActivity.start) {
                MainActivity.start = false;
            } else if (MainActivity.restart) {
                MainActivity.restart = false;
            }

            //set up the game variables on start/restart
            points = 0;
            hit = false;
            gameUpdate = true;
            count = 0;
            newHighScore = false;
            countHS = 0;
        }

    }

    //overirdden function
    @Override
    public void draw(Canvas canvas) {

        //calls the base class draw function
        super.draw(canvas);
        if (canvas != null) {

            //always draw background
            background.draw(canvas);

            //if densmore code is inputted
            if (MainActivity.makeDensmore) {
                countDensmore++;
                if (countDensmore < 100) {
                    displayHS.setTextSize((float) (30 * Background.scale));
                    displayHS.setColor(Color.RED);
                    displayHS.setFakeBoldText(true);
                    displayHS.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                    canvas.drawText("DENSMORE EQUIPPED", S_WIDTH / 2 - (float) (140 * Background.scale), (float) (30 * Background.scale), displayHS);
                }
            }

            //for when the game is going
            if (gameUpdate && !hit) {
                doodle.draw(canvas);
                obstacleDraw(obstacleArray, canvas);

                paintScore.setTextSize((float) (55 * Background.scale));

                //for when you get a new highscore
                if (newHighScore) {

                    //set score to red if new highscore
                    paintScore.setColor(Color.RED);
                    countHS++;

                    //only display for 100 ticks
                    if (countHS < 100) {
                        displayHS.setTextSize((float) (30 * Background.scale));
                        displayHS.setColor(Color.WHITE);
                        displayHS.setFakeBoldText(true);
                        displayHS.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                        canvas.drawText("NEW HIGHSCORE!", S_WIDTH / 2 - (float) (115 * Background.scale), (float) (30 * Background.scale), displayHS);


                        displayHSO.setStyle(Paint.Style.STROKE);
                        displayHSO.setStrokeWidth((int) (2 * Background.scale));
                        displayHSO.setTextSize((float) (30 * Background.scale));
                        displayHSO.setColor(Color.RED);
                        displayHSO.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                        canvas.drawText("NEW HIGHSCORE!", S_WIDTH / 2 - (float) (115 * Background.scale), (float) (30 * Background.scale), displayHSO);
                    }
                } else {
                    //set score to black if not new highscore
                    paintScore.setColor(Color.BLACK);
                }

                //draw score
                canvas.drawText("" + (int) points, (float) (11 * Background.scale), (float) (55 * Background.scale), paintScore);
            }

            //for mainmenu view
            if (!gameUpdate && !MainActivity.stats) {

                highScore.setTextSize((float) (34 * Background.scale));
                highScore.setColor(Color.BLACK);

                mode.setTextSize((float) (28 * Background.scale));
                mode.setFakeBoldText(true);
                canvas.drawText("Highscore: " + (int) saveOverallHighScore, (float) (11 * Background.scale), (float) (50 * Background.scale), highScore);

                gameOver.setTextSize((float) (110 * Background.scale));
                gameOver.setColor(Color.BLUE);
                gameOver.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                canvas.drawText("DENSMORE DASH", (float) (55 * Background.scale), (float) (200 * Background.scale), gameOver);

                outline.setStyle(Paint.Style.STROKE);
                outline.setStrokeWidth((int) (4.4 * Background.scale));
                outline.setTextSize((float) (110 * Background.scale));
                outline.setColor(Color.GREEN);
                outline.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                canvas.drawText("DENSMORE DASH", (float) (55 * Background.scale), (float) (200 * Background.scale), outline);

                //for gamemode choice
                if (gameMode == 2) {

                    mode.setColor(Color.RED);
                    canvas.drawText("Hard", S_WIDTH / 2 - (float) (31 * Background.scale), S_HEIGHT / 2 + (float) (185 * Background.scale), mode);
                } else if (gameMode == 1) {

                    mode.setColor(Color.YELLOW);
                    canvas.drawText("Medium", S_WIDTH / 2 - (float) (50 * Background.scale), S_HEIGHT / 2 + (float) (185 * Background.scale), mode);
                } else if (gameMode == 0) {

                    mode.setColor(Color.GREEN);
                    canvas.drawText("Easy", S_WIDTH / 2 - (float) (31 * Background.scale), S_HEIGHT / 2 + (float) (185 * Background.scale), mode);
                }
            }

            //if you lose
            if (hit && gameUpdate) {

                gameOver.setTextSize((float) (110 * Background.scale));
                gameOver.setColor(Color.WHITE);
                gameOver.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                canvas.drawText("GAME OVER", S_WIDTH / 2 - (float) (306 * Background.scale), (S_HEIGHT / 2) - (float) (125 * Background.scale), gameOver);

                outline.setStyle(Paint.Style.STROKE);
                outline.setStrokeWidth((int) (4.4 * Background.scale));
                outline.setTextSize((float) (110 * Background.scale));
                outline.setColor(Color.BLACK);
                outline.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                canvas.drawText("GAME OVER", S_WIDTH / 2 - (float) (306 * Background.scale), (S_HEIGHT / 2) - (float) (125 * Background.scale), outline);

                //if you got a new highscore
                if (points == saveOverallHighScore) {
                    displayHS.setTextSize((float) (83 * Background.scale));
                    displayHS.setColor(Color.WHITE);
                    displayHS.setFakeBoldText(true);
                    displayHS.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                    canvas.drawText("NEW HIGHSCORE!", (float) (160 * Background.scale), (S_HEIGHT / 2) - (float) (30 * Background.scale), displayHS);

                    displayHSO.setStyle(Paint.Style.STROKE);
                    displayHSO.setStrokeWidth((int) (4 * Background.scale));
                    displayHSO.setTextSize((float) (83 * Background.scale));
                    displayHSO.setColor(Color.RED);
                    displayHSO.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                    canvas.drawText("NEW HIGHSCORE!", (float) (160 * Background.scale), (S_HEIGHT / 2) - (float) (30 * Background.scale), displayHSO);

                    endScore.setTextSize((float) (83 * Background.scale));
                    endScore.setColor(Color.RED);
                    canvas.drawText("Score: " + (int) points, S_WIDTH / 2 - (float) (167 * Background.scale), (S_HEIGHT / 2) + (float) (60 * Background.scale), endScore);
                } else {
                    endScore.setTextSize((float) (83 * Background.scale));
                    endScore.setColor(Color.BLACK);
                    canvas.drawText("Score: " + (int) points, S_WIDTH / 2 - (float) (167 * Background.scale), (S_HEIGHT / 2) + (float) (42 * Background.scale), endScore);
                }

                //displays touch to restart
                touchRestart.setTextSize((float) (28 * Background.scale));
                touchRestart.setColor(Color.BLACK);
                canvas.drawText("Touch to restart", S_WIDTH / 2 - (float) (110 * Background.scale), S_HEIGHT - (float) (17 * Background.scale), touchRestart);
            }

            //for when to show stats
            if (MainActivity.stats) {
                DecimalFormat format = new DecimalFormat("###.##");
                stats.setTextSize((float) (50 * Background.scale));
                stats.setColor(Color.BLACK);
                canvas.drawText("Overall Highscore: " + saveOverallHighScore, S_WIDTH / 2 - (float) (200 * Background.scale), (S_HEIGHT / 2) - (float) (200 * Background.scale), stats);
                canvas.drawText("Easy Highscore: " + easyHighScore, S_WIDTH / 2 - (float) (200 * Background.scale), (S_HEIGHT / 2) - (float) (150 * Background.scale), stats);
                canvas.drawText("Medium Highscore: " + mediumHighScore, S_WIDTH / 2 - (float) (200 * Background.scale), (S_HEIGHT / 2) - (float) (100 * Background.scale), stats);
                canvas.drawText("Hard Highscore: " + hardHighScore, S_WIDTH / 2 - (float) (200 * Background.scale), (S_HEIGHT / 2) - (float) (50 * Background.scale), stats);
                canvas.drawText("Games Played: " + gamesPlayed, S_WIDTH / 2 - (float) (200 * Background.scale), (S_HEIGHT / 2) - (float) (0 * Background.scale), stats);
                canvas.drawText("Average Score: " + format.format(averageScore), S_WIDTH / 2 - (float) (200 * Background.scale), (S_HEIGHT / 2) - (float) (-50 * Background.scale), stats);
            }

        }
    }

    //overirdden function
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //for when playing the game
        if (MainActivity.start || gameUpdate) {

            //for if you didn't lose
            if (!hit) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    doodle.goUp(true);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    doodle.goUp(false);
                }
                return super.onTouchEvent(event);
            } else {

                //for if you lost
                if (count > 25) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        return true;
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        MainActivity.restart = true;
                    }
                    return super.onTouchEvent(event);
                }
                return false;
            }
        }
        return false;
    }

    //check if a obstacle and player collided
    public boolean checkIndividualCollision(Obstacle obstacle, DoodleGuy doodle) {
        int dx = doodle.image.getWidth();
        int dy = doodle.image.getHeight();
        int ox = obstacle.image.getWidth();
        int oy = obstacle.image.getHeight();
        Rect recd = new Rect((int) doodle.x, (int) doodle.y, (int) doodle.x + dx, (int) doodle.y + dy);
        Rect reco = new Rect(obstacle.x, obstacle.y, obstacle.x + ox, obstacle.y + oy);
        return Rect.intersects(recd, reco);
    }

    //to check if a the obstacle array and obstacles collided
    public boolean checkCollision(Obstacle[] array, DoodleGuy doodle) {
        for (int i = 0; i < array.length; i++) {
            if (checkIndividualCollision(array[i], doodle)) {
                return true;
            }
        }
        return false;
    }

    //for hard mode get pair obstacle y value
    public static int getOther(Obstacle obstacle) {
        if (obstacle.y < 0) {
            return (int) (obstacle.y + 140 * Background.scale + S_HEIGHT);
        } else {
            return (int) (obstacle.y - 140 * Background.scale - S_HEIGHT);
        }
    }

    //draw obstacles from array
    private void obstacleDraw(Obstacle[] array, Canvas canvas) {
        for (int i = 0; i < array.length; i++) {
            array[i].draw(canvas);
        }
    }

    //update obstacles from array
    private void obstacleUpdate(Obstacle[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i].update();
        }
    }

    //update stats
    public void updateStats() {
        if (points > saveOverallHighScore) {
            editor1.putInt("overallHighScore", (int) points);
            editor1.apply();
            saveOverallHighScore = (int) points;
            newHighScore = true;
        }
        if (gameMode == 0 && points > easyHighScore) {
            editor2.putInt("easyHighScore", (int) points);
            editor2.commit();
            easyHighScore = (int) points;
        } else if (gameMode == 1 && points > mediumHighScore) {
            editor3.putInt("mediumHighScore", (int) points);
            editor3.commit();
            mediumHighScore = (int) points;
        } else if (gameMode == 2 && points > hardHighScore) {
            editor4.putInt("hardHighScore", (int) points);
            editor4.apply();
            hardHighScore = (int) points;
        }


    }
}
