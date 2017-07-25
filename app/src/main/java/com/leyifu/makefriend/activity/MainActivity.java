package com.leyifu.makefriend.activity;

//import android.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.adapter.ConversationListAdapterEx;
import com.leyifu.makefriend.fragment.ContactsFragment;
import com.leyifu.makefriend.fragment.LacationFragment;
import com.leyifu.makefriend.fragment.MeFragment;
import com.leyifu.makefriend.widget.MorePopWindow;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup rg_main_footer;
    private List<Fragment> fragments = new ArrayList<>();
    private TextView tv_head_main;
    private Fragment currentFragment;
    private ImageView iv_main_search;
    private ImageView iv_main_add;
//    private LinearLayout ll_not_connect;
//    private NetworkReceiver receiver;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      初始化UI
        initUI();

//        init();
//      初始化数据
        initDate();
        //连接
    }

    private void initUI() {
        tv_head_main = ((TextView) findViewById(R.id.tv_head_main));
        rg_main_footer = ((RadioGroup) findViewById(R.id.rg_main_footer));
        iv_main_search = ((ImageView) findViewById(R.id.iv_main_search));
        iv_main_add = ((ImageView) findViewById(R.id.iv_main_add));
//        ll_not_connect = ((LinearLayout) findViewById(R.id.ll_not_connect));

        iv_main_search.setOnClickListener(this);
        iv_main_add.setOnClickListener(this);
    }

    private void init() {

//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        receiver = new NetworkReceiver();
//        this.registerReceiver(receiver, filter);
    }

//    public class NetworkReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //注册广播监听网络
//            if (!Utils.utilsCheckNetWork(MainActivity.this)) {
//                ll_not_connect.setVisibility(View.VISIBLE);
//            } else {
//                ll_not_connect.setVisibility(View.GONE);
//            }
//
//        }
//    }


    private void initDate() {
        Fragment conversationList = initConversationList();

        rg_main_footer.setOnCheckedChangeListener(checkChangeListener);

        fragments.add(conversationList);
//        fragments.add(new NewsFragment());
        fragments.add(new LacationFragment());
        fragments.add(new ContactsFragment());
        fragments.add(new MeFragment());

        fragmentManager = getSupportFragmentManager();
        if (!fragments.get(0).isAdded()) {
            fragmentManager.beginTransaction().add(R.id.fragment_main,fragments.get(0)).commit();
            currentFragment = fragments.get(0);
        }
    }


    RadioGroup.OnCheckedChangeListener checkChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_main_footer_new:
                    addOrShowFragment(fragmentManager.beginTransaction(), fragments.get(0));
                    break;
                case R.id.rb_main_footer_lacation:
                    addOrShowFragment(fragmentManager.beginTransaction(), fragments.get(1));
                    break;
                case R.id.rb_main_footer_contacts:
                    addOrShowFragment(fragmentManager.beginTransaction(), fragments.get(2));
                    break;
                case R.id.rb_main_footer_me:
                    addOrShowFragment(fragmentManager.beginTransaction(), fragments.get(3));
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

    private ConversationListFragment mConversationListFragment = null;
    private boolean isDebug;
    private Conversation.ConversationType[] mConversationsTypes;

     private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            listFragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
            Uri uri;
            if (isDebug) {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM,
                        Conversation.ConversationType.DISCUSSION
                };

            } else {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM
                };
            }
            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
            return listFragment;
        } else {
            return mConversationListFragment;
        }
    }
}
