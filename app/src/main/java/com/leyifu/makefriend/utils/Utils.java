package com.leyifu.makefriend.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by hahaha on 2017/5/18 0018.
 */
public class Utils {

    private static Toast myToast;

    public static void startToast(Context context, String text) {

        if (myToast != null) {
            myToast.cancel();
            myToast =Toast.makeText(context,text,Toast.LENGTH_SHORT);
        }else{
            myToast =Toast.makeText(context,text,Toast.LENGTH_SHORT);
        }
        myToast.show();

    }

    public static boolean utilsCheckNetWork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        int type = activeNetworkInfo.getType();
        if (type == ConnectivityManager.TYPE_MOBILE || type == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
