package com.example.class23a_and_rocksgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    GameManager gameManager;

    final Handler handler = new Handler();
    final int DELAY = 1000;
    final int Lifes = 3;
    private Timer timer;
    private MaterialButton main_BTN_left;
    private MaterialButton main_BTN_right;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] main_IMG_cars;
    private ShapeableImageView[][] main_IMG_rocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameManager = new GameManager(Lifes);
        findViews();
        initViews();
    }


    private void refreshUI() {
        if (gameManager.isLose()) {
            showToast("Game Over");
            stopTimer();
            finish();
        } else {
            for (int i = 0; i < gameManager.getWrong(); i++) {
                main_IMG_hearts[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void showToast(String string) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, string, duration);
        toast.show();
    }

    private void findViews() {
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        setHeartsView();
        setCarsView();
        setRocksView();
    }

    private void setHeartsView(){
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3),
        };
    }

    private void setCarsView(){
        main_IMG_cars = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_car1),
                findViewById(R.id.main_IMG_car2),
                findViewById(R.id.main_IMG_car3),
        };
        main_IMG_cars[0].setVisibility(View.INVISIBLE);
        main_IMG_cars[2].setVisibility(View.INVISIBLE);
        gameManager.setCarPlace(1);
    }

    private void setRocksView(){
        main_IMG_rocks = new ShapeableImageView[gameManager.ROWS][gameManager.COLS];
        String rock = "main_IMG_rock";
        int current, i, j, index = 1;
        for (i = 0; i < gameManager.ROWS; i++){
            for(j = 0; j < gameManager.COLS; j++){
                current = getResources().getIdentifier(rock + index, "id", getPackageName());
                main_IMG_rocks[i][j] = findViewById(current);
                index++;
            }
        }
        for (i = 0; i < gameManager.ROWS; i++){
            for(j = 0; j < gameManager.COLS; j++){
                gameManager.activeRocks[i][j] = 0;
                main_IMG_rocks[i][j].setVisibility(View.INVISIBLE);
            }
        }
        main_IMG_rocks[0][gameManager.getNewRock()].setVisibility(View.VISIBLE);
    }

    private void initViews() {
        main_BTN_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(true);
            }
        });

        main_BTN_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(false);
            }
        });
        startTimer();
    }
    private void clicked(boolean answer) {
        gameManager.setPlace(answer, gameManager.getCarPlace());
        main_IMG_cars[gameManager.getCarPlace()].setVisibility(View.VISIBLE);
        for (int i = 0; i < main_IMG_cars.length; i++) {
            if(i != gameManager.getCarPlace())
                main_IMG_cars[i].setVisibility(View.INVISIBLE);
        }
    }

    Runnable runnable = new Runnable() {
        public void run() {
            handler.postDelayed(this, DELAY);
            updateRocksView();
        }
    };

    private void startTimer() {
        handler.postDelayed(runnable, DELAY);
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private void updateRocksView(){
        for (int i = 0; i < gameManager.COLS; i++){
            for(int j = 0; j < gameManager.ROWS; j++){
                if (gameManager.activeRocks[j][i] == 1){
                    if(j < gameManager.ROWS-1){
                        gameManager.activeRocks[j][i] = 0;
                        gameManager.activeRocks[j+1][i] = 1;
                        if (i < gameManager.COLS-1)
                            i++;
                    }
                    else {
                        gameManager.activeRocks[j][i] = 0;
                        if (gameManager.checkAccident() == 1) {
                            vibrate();
                            showToast("You lost your " + gameManager.getWrong() + " life");
                        }
                        refreshUI();
                        if(gameManager.getWrong() != main_IMG_hearts.length)
                            gameManager.getNewRock();
                    }
                }
            }
        }
        update();
    }

    private void update(){
        for (int i = 0; i < gameManager.ROWS; i++) {
            for (int j = 0; j < gameManager.COLS; j++) {
                if(gameManager.activeRocks[i][j] == 0)
                    main_IMG_rocks[i][j].setVisibility(View.INVISIBLE);
                else
                    main_IMG_rocks[i][j].setVisibility(View.VISIBLE);
            }
        }
    }

}