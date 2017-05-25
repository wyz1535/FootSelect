package com.leyifu.makefriend.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by hahaha on 2017/5/18 0018.
 */
public class BackgroundAlpha {

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */

    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }
}
