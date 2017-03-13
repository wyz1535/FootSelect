package com.leyifu.makefriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyifu.makefriend.R;

/**
 * Created by hahaha on 2017/3/13 0013.
 */
public class LandAndRegistActivity extends BaseActivity implements View.OnClickListener {

    private Button bt_land_regist;
    private TextView tv_new_user_regist;
    private EditText et_land_name;
    private EditText et_land_password;
    private TextView tv_forget_password;
    private ImageView iv_land_more_name_down;
    private ImageView iv_land_more_name_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_regist);

        initUI();
        init();
    }

    private void initUI() {
        bt_land_regist = ((Button) findViewById(R.id.bt_land_regist));
        tv_new_user_regist = ((TextView) findViewById(R.id.tv_new_user_regist));
        et_land_name = ((EditText) findViewById(R.id.et_land_name));
        et_land_password = ((EditText) findViewById(R.id.et_land_password));
        tv_forget_password = ((TextView) findViewById(R.id.tv_forget_password));
        iv_land_more_name_down = ((ImageView) findViewById(R.id.iv_land_more_name_down));
        iv_land_more_name_up = ((ImageView) findViewById(R.id.iv_land_more_name_up));
    }

    private void init() {
        tv_new_user_regist.setOnClickListener(this);
        bt_land_regist.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        iv_land_more_name_down.setOnClickListener(this);
        iv_land_more_name_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_new_user_regist:
                Intent registIntent = new Intent(LandAndRegistActivity.this, RegistActivity.class);
                startActivity(registIntent);
                break;
            case R.id.bt_land_regist:
                String name = et_land_name.getText().toString().trim();
                String password = et_land_password.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(LandAndRegistActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LandAndRegistActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (("zhangsan").equals(name) && ("123456".equals(password))) {
                    Intent mainIntent = new Intent(LandAndRegistActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Toast.makeText(LandAndRegistActivity.this, "账号密码不正确", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_land_more_name_down:
                iv_land_more_name_down.setVisibility(View.INVISIBLE);
                iv_land_more_name_up.setVisibility(View.VISIBLE);
                Toast.makeText(LandAndRegistActivity.this, "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_land_more_name_up:
                iv_land_more_name_down.setVisibility(View.VISIBLE);
                iv_land_more_name_up.setVisibility(View.INVISIBLE);
                Toast.makeText(LandAndRegistActivity.this, "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_forget_password:
                Toast.makeText(LandAndRegistActivity.this, "忘记密码", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
