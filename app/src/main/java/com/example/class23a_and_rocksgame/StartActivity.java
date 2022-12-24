package com.example.class23a_and_rocksgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class StartActivity extends AppCompatActivity {

    private MaterialButton main_BTN_startgame;
    private MaterialButton main_BTN_score;
    private MaterialButton main_BTN_withoutbuttons;
    private MaterialButton main_BTN_withbuttons;
    private MaterialTextView main_TXT_text;
    private EditText main_LBL_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViews();
        initViews();
    }

    private void findViews() {
        main_BTN_startgame = findViewById(R.id.start_BTN_startgame);
        main_BTN_score = findViewById(R.id.start_BTN_score);
        main_BTN_withoutbuttons = findViewById(R.id.start_BTN_withoutbuttons);
        main_BTN_withbuttons = findViewById(R.id.start_BTN_withbuttons);
        main_TXT_text = findViewById(R.id.start_TXT_text);
        main_LBL_name = findViewById(R.id.start_LBL_name);
    }

    private void initViews() {
        main_BTN_startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked();
            }
        });

        main_BTN_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScorePage();
            }
        });
    }

    private void openScorePage() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
        finish();
    }

    private void openGamePage(String chose) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.KEY_STATUS,chose);
        startActivity(intent);
        finish();
    }

    private void clicked(){
        main_BTN_withbuttons.setVisibility(View.VISIBLE);
        main_BTN_withoutbuttons.setVisibility(View.VISIBLE);
        main_TXT_text.setVisibility(View.VISIBLE);
        main_LBL_name.setVisibility(View.VISIBLE);
        initNextViews();
    }

    private void initNextViews() {
        if(main_LBL_name.getText().toString().isEmpty()){
            showToast("Please enter your name");
        }
       // MySPV3.getInstance().putString("0", main_LBL_name.getText().toString());
        main_BTN_withbuttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGamePage("true");
            }
        });

        main_BTN_withoutbuttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGamePage("false");
            }
        });
    }

    private void showToast(String string) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, string, duration);
        toast.show();
    }
}