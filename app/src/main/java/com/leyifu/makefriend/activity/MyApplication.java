package com.leyifu.makefriend.activity;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by hahaha on 2017/3/20 0020.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
