package com.leyifu.makefriend.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.utils.Utils;
import com.leyifu.makefriend.view.CharacterParser;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class ChatInfomationActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ChatInfomationActivity.class.getSimpleName();
    private TextView tv_me_title;
    private ImageView iv_me_back;
    private TextView clear_chat_record;
    private TextView tv_find_chat_record;
    private TextView tv_message_no_disturbing;
    private TextView tv_message_top;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_infomation);

        initUI();
        init();

    }

    private void initUI() {
        tv_me_title = ((TextView) findViewById(R.id.tv_me_title));
        iv_me_back = ((ImageView) findViewById(R.id.iv_me_back));
        clear_chat_record = ((TextView) findViewById(R.id.clear_chat_record));
        tv_find_chat_record = ((TextView) findViewById(R.id.tv_find_chat_record));
        tv_message_no_disturbing = ((TextView) findViewById(R.id.tv_message_no_disturbing));
        tv_message_top = ((TextView) findViewById(R.id.tv_message_top));
    }

    private void init() {
        tv_me_title.setText(R.string.chat_infomation);
        iv_me_back.setOnClickListener(this);
        clear_chat_record.setOnClickListener(this);
        tv_find_chat_record.setOnClickListener(this);
        tv_message_no_disturbing.setOnClickListener(this);
        tv_message_top.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_me_back:
                finish();
                break;
            case R.id.tv_find_chat_record:
//                public void getHistoryMessages(final Conversation.ConversationType conversationType,
// final String targetId, final int oldestMessageId, final int count,
// final ResultCallback<List<Message>> callback)
                RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.PRIVATE,
                        "NnlkM1cnI", -1, 100, new RongIMClient.ResultCallback<List<Message>>() {
                            @Override
                            public void onSuccess(List<Message> messages) {
                                for (int i = 0; i < messages.size(); i++) {
                                    SpannableStringBuilder record = CharacterParser.getInstance().getColoredChattingRecord("", messages.get(i).getContent());
                                    Log.e(TAG, "onSuccess:= " + record);
                                    Utils.startToast(ChatInfomationActivity.this,messages.get(i).toString());
                                }
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {

                            }
                        });

//                RongIMClient.getInstance().searchMessages(Conversation.ConversationType.PRIVATE,
//                        "NnlkM1cnI", "不行", 0, 0, new RongIMClient.ResultCallback<List<Message>>() {
//                            @Override
//                            public void onSuccess(List<Message> messages) {
//                                for (Message message : messages) {
//                                    SpannableStringBuilder record = CharacterParser.getInstance().getColoredChattingRecord("不行", message.getContent());
//                                    Log.e(TAG, "onSuccess:= " + record);
//                                }
//                            }
//
//                            @Override
//                            public void onError(RongIMClient.ErrorCode errorCode) {
//
//                            }
//                        });

                break;
            case R.id.tv_message_no_disturbing:
                break;
            case R.id.tv_message_top:
                break;
            case R.id.clear_chat_record:
                deleteChatRecord();
                break;
        }
    }

    private void deleteChatRecord() {

        //                public void deleteMessages(final Conversation.ConversationType conversationType,
//                                          final String targetId, final ResultCallback<Boolean> callback)
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.is_delete_chat_record)
                .setNegativeButton(R.string.me_account_canal, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.me_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                RongIM.getInstance().deleteMessages(Conversation.ConversationType.PRIVATE, "NnlkM1cnI", new RongIMClient.ResultCallback<Boolean>() {
                                    @Override
                                    public void onSuccess(Boolean aBoolean) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Utils.startToast(ChatInfomationActivity.this, "清理成功");
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode errorCode) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Utils.startToast(ChatInfomationActivity.this, "清理失败");
                                            }
                                        });

                                    }
                                });
                            }
                        }).start();
                    }
                }).show();
    }
}
