package com.leyifu.makefriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.utils.ActivityCollection;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_me_title;
    private TextView tv_updata_passwoed;
    private TextView tv_privacy;
    private TextView tv_new_news;
    private TextView tv_clear_cache;
    private ImageView iv_me_back;
    private Button bt_quit_land;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUI();
    }

    private void initUI() {
        iv_me_back = ((ImageView) findViewById(R.id.iv_me_back));
        tv_me_title = ((TextView) findViewById(R.id.tv_me_title));
        tv_updata_passwoed = ((TextView) findViewById(R.id.tv_updata_passwoed));
        tv_privacy = ((TextView) findViewById(R.id.tv_privacy));
        tv_new_news = ((TextView) findViewById(R.id.tv_new_news));
        tv_clear_cache = ((TextView) findViewById(R.id.tv_clear_cache));
        bt_quit_land = ((Button) findViewById(R.id.bt_quit_land));

        tv_me_title.setText("设置");
        iv_me_back.setOnClickListener(this);
        tv_updata_passwoed.setOnClickListener(this);
        tv_privacy.setOnClickListener(this);
        tv_new_news.setOnClickListener(this);
        tv_clear_cache.setOnClickListener(this);
        bt_quit_land.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_me_back:
                finish();
                break;
            case R.id.tv_updata_passwoed:
                startActivity(new Intent(this,UpdataPasswordActivity.class));
                break;
            case R.id.tv_privacy:
                startActivity(new Intent(this,PrivacyActivity.class));
                break;
            case R.id.tv_new_news:
                break;
            case R.id.tv_clear_cache:
                break;
            case R.id.bt_quit_land:
                ActivityCollection.finishAll();
                startActivity(new Intent(this,LandAndRegistActivity.class));
                break;
        }
    }
}
