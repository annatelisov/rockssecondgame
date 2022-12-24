package com.example.class23a_and_rocksgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
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
import com.google.android.material.textview.MaterialTextView;


public class MainActivity extends AppCompatActivity {

    GameManager gameManager;

    public static String KEY_STATUS;
    final Handler handler = new Handler();
    final int DELAY = 1000;
    private int index = 0;
    private int score = 0;
    String status = "";
    private MaterialTextView main_LBL_scoreNum;
    private MaterialButton main_BTN_left;
    private MaterialButton main_BTN_right;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] main_IMG_cars;
    private ShapeableImageView[][] main_IMG_rocks;
    private moveDecoretor moveDecoretor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent previousIntent = getIntent();
        status = previousIntent.getExtras().getString(KEY_STATUS);

        gameManager = new GameManager();
        if (status.equals("false")) {
            moveDecoretor = new moveDecoretor(this, callBack_moves);
        }
        findViews();
        initViews();
    }

    private moveDecoretor.CallBack_moves callBack_moves = new moveDecoretor.CallBack_moves() {
        @Override
        public void car1Step() {
            gameManager.setCarPlace(0);
            setVisibility();
        }

        @Override
        public void car2Step() {
            gameManager.setCarPlace(1);
            setVisibility();
        }

        @Override
        public void car3Step() {
            gameManager.setCarPlace(2);
            setVisibility();
        }

        @Override
        public void car4Step() {
            gameManager.setCarPlace(3);
            setVisibility();
        }

        @Override
        public void car5Step() {
            gameManager.setCarPlace(4);
            setVisibility();
        }
    };

    private void openScorePage() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
        finish();
    }

    private void refreshUI() {
        if (gameManager.isLose()) {
            stopTimer();
            openScorePage();
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
        if (status.equals("false")) {
            main_BTN_right.setVisibility(View.INVISIBLE);
            main_BTN_left.setVisibility(View.INVISIBLE);
        }
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
                findViewById(R.id.main_IMG_car4),
                findViewById(R.id.main_IMG_car5),
        };
        main_IMG_cars[0].setVisibility(View.INVISIBLE);
        main_IMG_cars[1].setVisibility(View.INVISIBLE);
        main_IMG_cars[3].setVisibility(View.INVISIBLE);
        main_IMG_cars[4].setVisibility(View.INVISIBLE);
        gameManager.setCarPlace(2);
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
        gameManager.getNewRock();
        update();
    }

    private void initViews() {
        if (status.equals("true")) {
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
        }
        startTimer();
    }
    private void clicked(boolean answer) {
        gameManager.setPlace(answer, gameManager.getCarPlace());
        setVisibility();
    }

    private void setVisibility(){
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

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        if (status.equals("false")) {
            moveDecoretor.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
        if (status.equals("false")) {
            moveDecoretor.start();
        }
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
                    if(j != gameManager.ROWS-1){
                        gameManager.setActiveRocks(j, i, 0);
                        gameManager.setActiveRocks(j+1, i, 1);
                        if (i != gameManager.COLS-1)
                            i++;
                    }
                    else {
                        gameManager.setActiveRocks(j, i, 0);
                        if (gameManager.checkAccident(i) == 1) {
                            vibrate();
                            showToast("You lost your " + gameManager.getWrong() + " life");
                        }
                        refreshUI();
                        if(gameManager.getWrong() != gameManager.Lifes)
                            gameManager.getNewRock();
                    }
                }
                else if(gameManager.activeRocks[j][i] == 2){
                    if(j != gameManager.ROWS-1){
                        gameManager.setActiveRocks(j, i, 0);
                        main_IMG_rocks[i][j].setImageResource(R.drawable.img_stone);
                        gameManager.setActiveRocks(j+1, i, 2);
                        if (i != gameManager.COLS-1)
                            i++;
                    }
                    else {
                        gameManager.setActiveRocks(j, i, 0);
                        score += gameManager.checkCoins(i);
                        refreshUI();
                    }
                }
            }
        }
        if(index % 3 == 0) {
            gameManager.getNewRock();
            gameManager.getNewCoin();
        }
        update();
    }

    private void update() {
        score++;
        index++;
        for (int i = 0; i < gameManager.ROWS; i++) {
            for (int j = 0; j < gameManager.COLS; j++) {
                if (gameManager.activeRocks[i][j] == 0)
                    main_IMG_rocks[i][j].setVisibility(View.INVISIBLE);
                else if (gameManager.activeRocks[i][j] == 2)
                    main_IMG_rocks[i][j].setImageResource(R.drawable.img_dollar);
                else
                    main_IMG_rocks[i][j].setVisibility(View.VISIBLE);
            }
        }
    }
}
