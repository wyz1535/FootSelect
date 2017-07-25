package com.leyifu.makefriend;

import android.app.Application;

import com.leyifu.makefriend.constant.Constant;
import com.leyifu.makefriend.utils.Utils;
import com.squareup.leakcanary.LeakCanary;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by hahaha on 2017/3/20 0020.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        RongIM.init(this);

        initConnect();
    }

    private void initConnect() {
        /**
         * IMKit SDK调用第二步
         * 建立与服务器的连接
         */
        RongIM.connect(Constant.Token01, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
            }

            @Override
            public void onSuccess(String userId) {
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Utils.startToast(MyApplication.this,"连接失败");
            }
        });
    }
}
