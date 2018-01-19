package com.flower.testdemo;

import android.app.Application;
import android.util.Log;

/**
 * Created by flower on 2018/1/18.
 * 自定义Application
 */

public class App extends Application {
    public static final String TAG = App.class.getSimpleName();
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, getClass().getSimpleName() + " onCreate");
        instance = this;
    }
}