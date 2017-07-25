package com.leyifu.makefriend.utils;

import android.util.Log;

/**
 * Created by hahaha on 2017/7/10 0010.
 */

public class LogUtil {

    public static int VERBOSE = 1;
    public static int DEBUG = 2;
    public static int INFO = 3;
    public static int WARN = 4;
    public static int ERROR = 5;
    public static int NOTHING = 6;
    public static int level =VERBOSE;

    public static void v(String TAG, String msg) {
        if (level <= VERBOSE) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String TAG, String msg) {
        if (level <= DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (level <= INFO) {
            Log.v(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (level <= WARN) {
            Log.v(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (level <= ERROR) {
            Log.v(TAG, msg);
        }
    }
}
