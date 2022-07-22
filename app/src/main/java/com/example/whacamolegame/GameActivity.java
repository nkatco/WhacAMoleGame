package com.example.whacamolegame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private List<ImageButton> list = new ArrayList<>();
    private TextView TimerTextView, ScoreTextView;
    private ImageButton HoleButton1, HoleButton2, HoleButton3, HoleButton4, HoleButton5, HoleButton6;
    private ImageView scoreImageView;
    private static int score = 0;
    private int time = 30;
    private boolean inGame;
    private List <Integer> randoms = new ArrayList<>();

    private Vibrator vibrator;
    private Animation scoreAnimation;

    private AnimationDrawable AnimationMole, AnimationMole2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        inGame = true;

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        TimerTextView = findViewById(R.id.TimerTextView);
        ScoreTextView = findViewById(R.id.ScoreTextView);
        ScoreTextView.setText(String.valueOf(score));

        scoreImageView = findViewById(R.id.scoreImageView);

        HoleButton1 = findViewById(R.id.HoleButton1);
        HoleButton2 = findViewById(R.id.HoleButton2);
        HoleButton3 = findViewById(R.id.HoleButton3);
        HoleButton4 = findViewById(R.id.HoleButton4);
        HoleButton5 = findViewById(R.id.HoleButton5);
        HoleButton6 = findViewById(R.id.HoleButton6);

        HoleButton1.setTag(0);
        HoleButton2.setTag(0);
        HoleButton3.setTag(0);
        HoleButton4.setTag(0);
        HoleButton5.setTag(0);
        HoleButton6.setTag(0);

        HoleButton1.setOnClickListener(this);
        HoleButton2.setOnClickListener(this);
        HoleButton3.setOnClickListener(this);
        HoleButton4.setOnClickListener(this);
        HoleButton5.setOnClickListener(this);
        HoleButton6.setOnClickListener(this);

        list.add(HoleButton1);
        list.add(HoleButton2);
        list.add(HoleButton3);
        list.add(HoleButton4);
        list.add(HoleButton5);
        list.add(HoleButton6);

        UpdateHole();
        Timer();
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.HoleButton1:
                if(HoleButton1.getTag().equals(1)) {
                    setScore(1);
                    animScore();
                    kickMole(HoleButton1);
                    HoleButton1.setTag(0);
                    break;
                } else {
                    score--;
                    vibrate(100);
                    setScore(-1);
                    break;
                }
            case R.id.HoleButton2:
                if(HoleButton2.getTag().equals(1)) {
                    setScore(1);
                    animScore();
                    kickMole(HoleButton2);
                    HoleButton2.setTag(0);
                    break;
                } else {
                    setScore(-1);
                    vibrate(100);
                    break;
                }
            case R.id.HoleButton3:
                if(HoleButton3.getTag().equals(1)) {
                    setScore(1);
                    animScore();
                    kickMole(HoleButton3);
                    HoleButton3.setTag(0);
                    break;
                } else {
                    setScore(-1);
                    vibrate(100);
                    break;
                }
            case R.id.HoleButton4:
                if(HoleButton4.getTag().equals(1)) {
                    setScore(1);
                    animScore();
                    kickMole(HoleButton4);
                    HoleButton4.setTag(0);
                    break;
                } else {
                    setScore(-1);
                    vibrate(100);
                    break;
                }
            case R.id.HoleButton5:
                if(HoleButton5.getTag().equals(1)) {
                    setScore(1);
                    animScore();
                    kickMole(HoleButton5);
                    HoleButton5.setTag(0);
                    break;
                } else {
                    setScore(-1);
                    vibrate(100);
                    break;
                }
            case R.id.HoleButton6:
                if(HoleButton6.getTag().equals(1)) {
                    setScore(1);
                    animScore();
                    kickMole(HoleButton6);
                    HoleButton6.setTag(0);
                    break;
                } else {
                    setScore((-1));
                    vibrate(100);
                    break;
                }
        }
    }
    public void UpdateHole() {
        Thread mThread = new Thread(new Runnable() {
            int a;
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(inGame) {
                    a = getRandomNumber(0, 5);
                    try {
                        for (int i = randoms.size(); i >= randoms.size() - 2; i--) {
                            while (a == randoms.get(i)) {
                                a = getRandomNumber(0, 5);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    randoms.add(a);
                    list.get(a).setTag(1);
                    runOnUiThread(() -> {
                        list.get(a).setBackgroundResource(R.drawable.mole_up);
                        AnimationMole = (AnimationDrawable) list.get(a).getBackground();
                        AnimationMole.start();
                    });
                    try {
                        Thread.sleep(800 - MainActivity.difficulty);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AnimationMole.stop();
                    runOnUiThread(() -> {
                        list.get(a).setBackgroundResource(R.drawable.mole_down);
                        AnimationMole = (AnimationDrawable) list.get(a).getBackground();
                        AnimationMole.start();
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.get(a).setTag(0);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AnimationMole.stop();
                }
            }
        }); mThread.start();
    }
    public void Timer() {
            Thread mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(time > 0 && inGame) {
                        time--;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TimerTextView.setText("Time:" + time);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    getEndActivity();
                }
            }); mThread.start();
    }
    public void kickMole(ImageButton imageButton) {
        Thread kickMoleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if ((AnimationDrawable) imageButton.getBackground() == AnimationMole) {
                    AnimationMole.stop();
                }
                runOnUiThread(() -> {
                    imageButton.setBackgroundResource(R.drawable.mole_kick);
                    AnimationMole2 = (AnimationDrawable) imageButton.getBackground();
                    AnimationMole2.start();
                });
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AnimationMole2.stop();
            }
        }); kickMoleThread.start();
    }
    public void animScore() {
        Thread scoreAnimThread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void run() {
                scoreAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.score_anim);
                runOnUiThread(() -> {
                    scoreImageView.setLeft(getRandomNumber(0, getDisplay().getWidth()));
                    scoreImageView.setTop(getRandomNumber(0, 200));
                    scoreImageView.setVisibility(View.GONE);
                    scoreImageView.startAnimation(scoreAnimation);
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    scoreImageView.setVisibility(View.INVISIBLE);
                });
            }
        }); scoreAnimThread.start();
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public void setScore(int a) {
        score = score + a;
        if (score < 0) {
            score = 0;
            getEndActivity();
        }
        ScoreTextView.setText("Score: " + score);
    }
    public static void resetScore() {
        score = 0;
    }
    public static int getScore() {
        return score;
    }
    public void getEndActivity() {
        inGame = false;
        if (MainActivity.getRecordScore() < score) {
            MainActivity.setRecordScore(score);
        }
        Intent intent = new Intent(this, EndActivity.class);
        startActivity(intent);
    }
    public void vibrate(int time) {
        if (MainActivity.vibration) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(time);
            }
        }
    }
}