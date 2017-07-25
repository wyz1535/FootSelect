package com.leyifu.makefriend.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.leyifu.makefriend.R;

import io.rong.imkit.RongIM;

public class UserDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "UserDetailActivity";
    private ImageView iv_me_back;
    private TextView tv_me_title;
    private ImageView iv_white_point;
    private TextView tv_remark;
    private String name;
    private Button bt_quit_land;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");

        initUI();
    }

    private void initUI() {
        iv_me_back = ((ImageView) findViewById(R.id.iv_me_back));
        tv_me_title = ((TextView) findViewById(R.id.tv_me_title));
        iv_white_point = ((ImageView) findViewById(R.id.iv_white_point));
        tv_remark = ((TextView) findViewById(R.id.tv_remark));
        bt_quit_land = ((Button) findViewById(R.id.bt_quit_land));

        tv_me_title.setText("详细资料");
        iv_white_point.setVisibility(View.VISIBLE);

        if (!"".equals(name)) {
            tv_remark.setVisibility(View.VISIBLE);
        }
        iv_me_back.setOnClickListener(this);
        iv_white_point.setOnClickListener(this);
        bt_quit_land.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_me_back:
                finish();
                break;
            case R.id.iv_white_point:
                showPopup();
                break;
            case R.id.bt_quit_land:
//                startActivity(new Intent(this, ConversationActivity.class));

                /**
                 * 启动单聊
                 * context - 应用上下文。
                 * targetUserId - 要与之聊天的用户 Id。
                 * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
                 */
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(UserDetailActivity.this, "NnlkM1cnI", "helloworld");
                }
                finish();
                break;
        }
    }

    private void showPopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_black_list, null);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 设置SelectPicPopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 刷新状态
        popupWindow.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        popupWindow.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        popupWindow.setAnimationStyle(R.style.AnimationPreview);
        popupWindow.showAsDropDown(iv_white_point, 0, 10);
    }
}
