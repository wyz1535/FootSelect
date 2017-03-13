package com.leyifu.makefriend.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.leyifu.makefriend.R;

/**
 * Created by hahaha on 2017/3/13 0013.
 */
public class RegistActivity extends BaseActivity {

    private final String TAG = "RegistActivity";

    private CheckBox cb_read_terms;
    private Button bt_land_regist;
    private EditText et_regist_nama;
    private EditText et_regist_password;
    private EditText et_regist_sure_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initUI();
        init();

    }

    private void initUI() {
        cb_read_terms = ((CheckBox) findViewById(R.id.cb_read_terms));
        bt_land_regist = ((Button) findViewById(R.id.bt_land_regist));
        et_regist_nama = ((EditText) findViewById(R.id.et_regist_nama));
        et_regist_password = ((EditText) findViewById(R.id.et_regist_password));
        et_regist_sure_password = ((EditText) findViewById(R.id.et_regist_sure_password));

    }

    private void init() {
        //先判断是否阅读服务条款
        cb_read_terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!cb_read_terms.isChecked()) {
                    bt_land_regist.setClickable(false);
                } else {
                    bt_land_regist.setClickable(true);
                }
            }
        });


        bt_land_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //校验输入的是否为空 或者两次输入的密码是否一样
                virifyEdit();
            }
        });
    }

    private void virifyEdit() {
        String userName = et_regist_nama.getText().toString().trim();
        String password = et_regist_password.getText().toString().trim();
        String usrePassword = et_regist_sure_password.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(RegistActivity.this, "用户名为空", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "userName=" + userName);
            return;
        }
        if (TextUtils.isEmpty(password) && TextUtils.isEmpty(usrePassword)) {
            Toast.makeText(RegistActivity.this, "密码为空", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "password=" + password + "usrePassword=" + usrePassword);
            return;
        }

        int length = password.length();
        if (length < 6) {
            Toast.makeText(RegistActivity.this, "密码不能少于6位", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(usrePassword)) {
            Toast.makeText(RegistActivity.this, "两次密码不一样", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
    }
}
