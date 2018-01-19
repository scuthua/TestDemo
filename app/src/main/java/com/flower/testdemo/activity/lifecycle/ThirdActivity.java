package com.flower.testdemo.activity.lifecycle;

import android.os.Bundle;

import com.flower.testdemo.R;

public class ThirdActivity extends BaseLifeCycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_third);
        super.onCreate(savedInstanceState);
    }
}