package com.flower.testdemo.activity.lifecycle;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.flower.testdemo.App;
import com.flower.testdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flower on 2018/1/18.
 * 测试activity生命周期的Activity的父类
 */

public abstract class BaseLifeCycleActivity extends AppCompatActivity implements View.OnClickListener {
    protected final String TAG = getClass().getSimpleName();
    private static final List<String> allActivityNames = new ArrayList<>();

    static {
        try {
            Context context = App.getInstance();
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
            for (ActivityInfo activityInfo : pi.activities) {
                allActivityNames.add(activityInfo.name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String name = getClass().getSimpleName();
    private Spinner selectActivitySpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(App.TAG, getClass().getSimpleName() + "  onCreate");
        if (savedInstanceState != null) {
            name = savedInstanceState.getString(getClass().getSimpleName());
        }
        initView();
    }

    protected void initView() {
        // 设置actionbar的title
        setTitle(name);
        selectActivitySpinner = findViewById(R.id.spinner_select_activity_to_start);
        if (selectActivitySpinner != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allActivityNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selectActivitySpinner.setAdapter(adapter);
        }
        View gotoNextActivity = findViewById(R.id.btn_goto_next_activity);
        if (gotoNextActivity != null) {
            gotoNextActivity.setOnClickListener(this);
        }
        View throwException = findViewById(R.id.btn_throw_exception);
        if (throwException != null) {
            throwException.setOnClickListener(this);
        }
        View throwExceptionInBackground = findViewById(R.id.btn_throw_exception_in_background);
        if (throwExceptionInBackground != null) {
            throwExceptionInBackground.setOnClickListener(this);
        }
        View outOfMemory = findViewById(R.id.btn_out_of_memory);
        if (outOfMemory != null) {
            outOfMemory.setOnClickListener(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String value = "Saved " + getClass().getSimpleName();
        Log.e(App.TAG, getClass().getSimpleName() + "  onSaveInstanceState: " + value);
        outState.putString(getClass().getSimpleName(), value);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(App.TAG, getClass().getSimpleName() + "  onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(App.TAG, getClass().getSimpleName() + "  onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(App.TAG, getClass().getSimpleName() + "  onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(App.TAG, getClass().getSimpleName() + "  onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(App.TAG, getClass().getSimpleName() + "  onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(App.TAG, getClass().getSimpleName() + "  onDestroy");
    }


    /**
     * 直接在界面抛出异常
     */
    public void throwException(View view) {
        int result = 1 / 0;
    }

    /**
     * 模拟home键点击，然app进入后台，1second后然后抛出异常
     */
    public void throwExceptionInBackground(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "throwExceptionInBackground");
                int result = 1 / 0;
            }
        }, 1000);
    }

    /**
     * 模拟home键点击，然app进入后台，然后提示用adb实现activiy因内存不足被删除的情况
     */
    public void outOfMemory(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        // 在as自带的Terminal中执行：adb shell am kill packageName
        // 用自己的包名替换packageName，如com.flower.testdemo
        String command = String.format("adb shell am kill %1$s", getPackageName());
        Toast.makeText(this, "在as自带的Terminal中执行：" + command, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "outOfMemory: " + command);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_goto_next_activity:
                if (selectActivitySpinner != null && selectActivitySpinner.getSelectedItem() != null) {
                    String selectedActivity = (String) selectActivitySpinner.getSelectedItem();
                    try {
                        Class clazz = Class.forName(selectedActivity);
                        Intent intent = new Intent(this, clazz);
                        this.startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_throw_exception:
                throwException(v);
                break;
            case R.id.btn_throw_exception_in_background:
                throwExceptionInBackground(v);
                break;
            case R.id.btn_out_of_memory:
                outOfMemory(v);
                break;

        }
    }
}

/*

 */