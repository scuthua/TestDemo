package com.flower.testdemo.activity.lifecycle;

import android.os.Bundle;

import com.flower.testdemo.R;

public class FirstActivity extends BaseLifeCycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_first);
        super.onCreate(savedInstanceState);
    }
}