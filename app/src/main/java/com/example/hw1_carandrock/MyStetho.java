package com.example.hw1_carandrock;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyStetho extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
