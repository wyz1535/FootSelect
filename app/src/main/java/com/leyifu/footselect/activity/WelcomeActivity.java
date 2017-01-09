package com.leyifu.footselect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyifu.footselect.R;
import com.leyifu.footselect.utils.CountDownTimer;

import java.text.SimpleDateFormat;

/**
 * Created by hahaha on 2017/1/9 0009.
 */
public class WelcomeActivity extends Activity {

    private static final String TAG = "MainActivity";
    private TextView tv_count_down;
    private LinearLayout ll_skip_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initUI();

        initDate();
    }

    private void initUI() {
        tv_count_down = ((TextView) findViewById(R.id.tv_count_down));
        ll_skip_time = ((LinearLayout) findViewById(R.id.ll_skip_time));
    }

    private void initDate() {
        countDownTimer.start();
        ll_skip_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    CountDownTimer countDownTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("s");
            String time = simpleDateFormat.format(millisUntilFinished);
            Log.e(TAG, "millisUntilFinished=" + millisUntilFinished);
            tv_count_down.setText("跳过 " + time + "s");
        }

        @Override
        public void onFinish() {
            tv_count_down.setText("跳过 0s");
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }
    };
}
