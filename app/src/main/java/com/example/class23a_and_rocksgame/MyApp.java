package com.example.class23a_and_rocksgame;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySPV3.init(this);
        MySignal.init(this);
    }
}
