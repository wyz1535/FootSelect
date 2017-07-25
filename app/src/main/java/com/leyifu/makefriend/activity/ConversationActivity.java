package com.leyifu.makefriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyifu.makefriend.R;

/**
 * 聊天界面
 */

public class ConversationActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_me_title;
    private ImageView iv_me_back, iv_tab_me_hover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        //继承的是ActionBarActivity，直接调用 自带的 Actionbar，下面是Actionbar 的配置，如果不用可忽略…
        initUI();

        init();

//        initConnect();
    }

    private void initUI() {
        tv_me_title = ((TextView) findViewById(R.id.tv_me_title));
        iv_me_back = ((ImageView) findViewById(R.id.iv_me_back));
        iv_tab_me_hover = ((ImageView) findViewById(R.id.iv_tab_me_hover));

        iv_me_back.setOnClickListener(this);
        iv_tab_me_hover.setOnClickListener(this);
    }

    private void init() {
        //// TODO: 2017/7/3 0003  用户昵称
        tv_me_title.setText("聊天中...");
        iv_tab_me_hover.setVisibility(View.VISIBLE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_me_back:
                finish();
                break;
            case R.id.iv_tab_me_hover:
                startActivity(new Intent(this,ChatInfomationActivity.class));
//                deleteChatRecord();
                break;
        }
    }


}
