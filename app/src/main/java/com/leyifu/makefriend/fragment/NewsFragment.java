package com.leyifu.makefriend.fragment;

import android.view.View;

import com.leyifu.makefriend.R;

import java.util.HashMap;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by hahaha on 2017/1/5 0005.
 */
public class NewsFragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();
    String Token = "KCxiV6Svstv5u9NEWMrGFuGUvoQ8oXg2dfx9x7S7PYP15OMV7V4MjjNogO2+zCLm4wEhEcxE6nrHrJT+BzK07YOQPVWgUFZL";//ygkvT5Vgp

    @Override
    protected void init(View view) {
        reconnect(view);
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_news;
    }

    private void reconnect(final View view) {
        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                HashMap<String, Boolean> hashMap = new HashMap<>();
                //会话类型 以及是否聚合显示
                hashMap.put(Conversation.ConversationType.PRIVATE.getName(), false);
                hashMap.put(Conversation.ConversationType.PUSH_SERVICE.getName(), true);
                hashMap.put(Conversation.ConversationType.SYSTEM.getName(), true);
                RongIM.getInstance().startConversationList(view.getContext(), hashMap);
//                startActivity(new Intent(RongCouldAcitivity.this, SubConversationListActivtiy.class));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
}
