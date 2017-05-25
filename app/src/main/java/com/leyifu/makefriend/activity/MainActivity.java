package com.leyifu.makefriend.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.fragment.ContactsFragment;
import com.leyifu.makefriend.fragment.LacationFragment;
import com.leyifu.makefriend.fragment.MeFragment;
import com.leyifu.makefriend.fragment.NewsFragment;
import com.leyifu.makefriend.widget.MorePopWindow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup rg_main_footer;
    private List<Fragment> fragments = new ArrayList<>();
    private android.app.FragmentManager fragmentManager;
    private TextView tv_head_main;
    private Fragment currentFragment;
    private ImageView iv_main_search;
    private ImageView iv_main_add;
//    private String[] radioText= new String[]{"消息","附近","联系人","个人中心",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar!=null) {
//            actionBar.hide();
//        }

//      初始化UI
        initUI();
//      初始化数据
        initDate();
    }

    private void initDate() {

        rg_main_footer.setOnCheckedChangeListener(checkChangeListener);

        fragments.add(new NewsFragment());
        fragments.add(new LacationFragment());
        fragments.add(new ContactsFragment());
        fragments.add(new MeFragment());

        fragmentManager = getFragmentManager();
        if (!fragments.get(0).isAdded()) {
            fragmentManager.beginTransaction().add(R.id.fragment_main, fragments.get(0)).commit();
            currentFragment = fragments.get(0);
        }
//        tv_head_main.setText(getResources().getString(R.string.radioNews));
    }

    private void initUI() {
        tv_head_main = ((TextView) findViewById(R.id.tv_head_main));
        rg_main_footer = ((RadioGroup) findViewById(R.id.rg_main_footer));
        iv_main_search = ((ImageView) findViewById(R.id.iv_main_search));
        iv_main_add = ((ImageView) findViewById(R.id.iv_main_add));

        iv_main_search.setOnClickListener(this);
        iv_main_add.setOnClickListener(this);
    }

    RadioGroup.OnCheckedChangeListener checkChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_main_footer_new:
                    addOrShowFragment(fragmentManager.beginTransaction(), fragments.get(0));
//                    tv_head_main.setText(getResources().getString(R.string.radioNews));
                    break;
                case R.id.rb_main_footer_lacation:
                    addOrShowFragment(fragmentManager.beginTransaction(), fragments.get(1));
//                    tv_head_main.setText(getResources().getString(R.string.radioLacation));
                    break;
                case R.id.rb_main_footer_contacts:
                    addOrShowFragment(fragmentManager.beginTransaction(), fragments.get(2));
//                    tv_head_main.setText(getResources().getString(R.string.radioContacts));
                    break;
                case R.id.rb_main_footer_me:
                    addOrShowFragment(fragmentManager.beginTransaction(), fragments.get(3));
//                    tv_head_main.setText(getResources().getString(R.string.radioMe));
                    break;
            }
        }

        private void addOrShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
            if (currentFragment == fragment) return;
            if (!fragment.isAdded()) {
                fragmentTransaction.hide(currentFragment).add(R.id.fragment_main, fragment).commit();
            } else {
                fragmentTransaction.hide(currentFragment).show(fragment).commit();
            }
            currentFragment = fragment;
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_main_search:
                break;
            case R.id.iv_main_add:
                MorePopWindow morePopWindow = new MorePopWindow(MainActivity.this);
                morePopWindow.showPopupWindow(iv_main_add);
                break;
        }
    }

}
