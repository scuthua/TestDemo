package com.flower.testdemo.activity.lifecycle;

import android.os.Bundle;

import com.flower.testdemo.R;

public class SecondActivity extends BaseLifeCycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_second);
        super.onCreate(savedInstanceState);
    }
}
