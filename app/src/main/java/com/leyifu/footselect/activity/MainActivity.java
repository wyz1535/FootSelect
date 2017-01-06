package com.leyifu.footselect.activity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.leyifu.footselect.R;
import com.leyifu.footselect.fragment.ContactsFragment;
import com.leyifu.footselect.fragment.LacationFragment;
import com.leyifu.footselect.fragment.MeFragment;
import com.leyifu.footselect.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_main_footer;
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentManager fragmentManager;
    private TextView tv_head_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg_main_footer = ((RadioGroup) findViewById(R.id.rg_main_footer));
        rg_main_footer.setOnCheckedChangeListener(checkChangeListener);
        ((RadioButton) rg_main_footer.getChildAt(0)).setChecked(true);

        fragments.add(new NewsFragment());
        fragments.add(new LacationFragment());
        fragments.add(new ContactsFragment());
        fragments.add(new MeFragment());

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_main,fragments.get(0)).commit();

        //配MIUI沉浸状态栏
        handleMaterialStatusBar();

        //初始化UI
        initUI();

        tv_head_main.setText("消息");

    }

    private void initUI() {
        tv_head_main = ((TextView) findViewById(R.id.tv_head_main));
    }

    RadioGroup.OnCheckedChangeListener checkChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_main_footer_new:
                    fragmentManager.beginTransaction().replace(R.id.fragment_main, fragments.get(0)).commit();
                    tv_head_main.setText("消息");
                    break;
                case R.id.rb_main_footer_lacation:
                    fragmentManager.beginTransaction().replace(R.id.fragment_main, fragments.get(1)).commit();
                    tv_head_main.setText("附近");
                    break;
                case R.id.rb_main_footer_contacts:
                    fragmentManager.beginTransaction().replace(R.id.fragment_main, fragments.get(2)).commit();
                    tv_head_main.setText("联系人");
                    break;
                case R.id.rb_main_footer_me:
                    fragmentManager.beginTransaction().replace(R.id.fragment_main, fragments.get(3)).commit();
                    tv_head_main.setText("个人中心");
                    break;
            }
        }
    };

    //点击返回两次确认退出
    private long mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {

                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 适配沉浸状态栏
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handleMaterialStatusBar() {
        // Not supported in APK level lower than 21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(0xff75B8F8);

    }
}
