package com.example.havenhub;

import android.app.Application;
import android.util.Log;
import com.tencent.mmkv.MMKV;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initMMKV();
    }

    private void initMMKV() {
        String rootDir = MMKV.initialize(this);
        Log.d("MMKV", "Application 初始化完成: " + rootDir);
    }
}