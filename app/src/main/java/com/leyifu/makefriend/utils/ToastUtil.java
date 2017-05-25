package com.leyifu.makefriend.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hahaha on 2017/5/18 0018.
 */
public class ToastUtil {

    public static void startToast(Context context, String text) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
