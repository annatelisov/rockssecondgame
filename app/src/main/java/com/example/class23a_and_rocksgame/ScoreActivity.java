package com.example.class23a_and_rocksgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textview.MaterialTextView;

public class ScoreActivity extends AppCompatActivity {

    private MaterialTextView score_LBL_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        findViews();
    }

    private void findViews() {
        score_LBL_score = findViewById(R.id.score_LBL_score);
    }
}