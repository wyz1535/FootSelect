package com.leyifu.makefriend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyifu.makefriend.R;

public class UpdataPasswordActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_me_title;
    private ImageView iv_me_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_password);

        initUI();
    }

    private void initUI() {
        iv_me_back = ((ImageView) findViewById(R.id.iv_me_back));
        tv_me_title = ((TextView) findViewById(R.id.tv_me_title));
        tv_me_title.setText("密码修改");

        iv_me_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_me_back:
                finish();
                break;
        }
    }
}