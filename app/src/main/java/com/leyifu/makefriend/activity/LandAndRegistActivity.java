package com.leyifu.makefriend.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.adapter.UserNameAdapter;
import com.leyifu.makefriend.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hahaha on 2017/3/13 0013.
 */
public class LandAndRegistActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LandAndRegistActivity";
    private Button bt_land_regist;
    private TextView tv_new_user_regist;
    private EditText et_land_name;
    private EditText et_land_password;
    private TextView tv_forget_password;
    private ImageView iv_land_more_name_down;

    Map<String, String> nameMap = new HashMap<String, String>();
    List<String> list = new ArrayList<String>();

    private ListView lv_pop;
    private PopupWindow popupWindow;
    private RelativeLayout rl_account;

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
        rl_account = ((RelativeLayout) findViewById(R.id.rl_account));
    }

    private void init() {
        tv_new_user_regist.setOnClickListener(this);
        bt_land_regist.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        if (list == null) {
            iv_land_more_name_down.setClickable(false);
        } else {
            iv_land_more_name_down.setOnClickListener(this);
        }
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
                String landName = null;
                String landPassword = null;

                DatabaseHelper dbHelper1 = new DatabaseHelper(this, "regist_db");
                //取得一个只读的数据库对象
                SQLiteDatabase db1 = dbHelper1.getReadableDatabase();

                Cursor cursor1 = db1.query("user", new String[]{"name", "password"}, null, null, null, null, null, null);

                while (cursor1.moveToNext()) {
                    landName = cursor1.getString(cursor1.getColumnIndex("name"));
                    landPassword = cursor1.getString(cursor1.getColumnIndex("password"));
                }
                db1.close();
                cursor1.close();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(LandAndRegistActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LandAndRegistActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((name).equals(landName) && (password.equals(landPassword))) {
                    Intent mainIntent = new Intent(LandAndRegistActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    Toast.makeText(LandAndRegistActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LandAndRegistActivity.this, "账号密码不正确", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_land_more_name_down:
//                iv_land_more_name_down.setImageDrawable(getResources().getDrawable(R.drawable.land_more_up));
                iv_land_more_name_down.setImageResource(R.drawable.land_more_up);
                DatabaseHelper helper = new DatabaseHelper(LandAndRegistActivity.this, "regist_db");
                SQLiteDatabase db = helper.getWritableDatabase();
                //创建游标对象
//                Cursor cursor = db.query("user", new String[]{"id", "name", "password"}, "id=?", new String[]{"1"}, null, null, null, null);

                Cursor cursor = db.query("user", null, null, null, null, null, null, null);
                list.clear();
                nameMap.clear();
                while (cursor.moveToNext()) {
                    String _id = cursor.getString(0);
                    String mName = cursor.getString(cursor.getColumnIndex("name"));
                    String mPassword = cursor.getString(cursor.getColumnIndex("password"));
                    //日志打印输出
                    Log.e(TAG, "_id-->" + _id + "mName-->" + mName + "mPassword-->" + mPassword);
                    nameMap.put(mName, mPassword);
                    list.add(mName);
                }

                db.close();
                cursor.close();
                Collections.reverse(list);

                for (Map.Entry<String, String> entry : nameMap.entrySet()) {
                    //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
                    //entry.getKey() ;entry.getValue(); entry.setValue();

//                    Log.e(TAG, "key= " + entry.getKey() + " and value= "
//                            + entry.getValue());
                }

                showPop(v);
                break;
            case R.id.tv_forget_password:
                Toast.makeText(LandAndRegistActivity.this, "忘记密码", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showPop(View view) {

        final View popView = LayoutInflater.from(LandAndRegistActivity.this).inflate(R.layout.pup_window, null);
        popupWindow = new PopupWindow(popView, rl_account.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT, true);

        lv_pop = ((ListView) popView.findViewById(R.id.lv_pop));

//        设置PopupWindow可触摸和获得焦点
        popupWindow.setTouchable(true);
//        popupWindow.setClippingEnabled(false);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setFocusable(false);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        popupWindow.showAsDropDown(et_land_name);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_land_more_name_down.setImageDrawable(getResources().getDrawable(R.drawable.land_more_down));
            }
        });

        final UserNameAdapter userNameAdapter = new UserNameAdapter(LandAndRegistActivity.this, list);
        lv_pop.setAdapter(userNameAdapter);

        lv_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                et_land_name.setText(list.get(position));
                //将光标设置在末尾
                et_land_name.setSelection(list.get(position).length());
                et_land_password.setText(nameMap.get(list.get(position)));
                et_land_password.setSelection(nameMap.get(list.get(position)).length());
                popupWindow.dismiss();
//                iv_land_more_name_down.setImageDrawable(getResources().getDrawable(R.drawable.land_more_down));
                iv_land_more_name_down.setImageResource(R.drawable.land_more_down);
                popupWindow = null;
            }
        });
        lv_pop.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(LandAndRegistActivity.this).setTitle("确认删除")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHelper helper = new DatabaseHelper(LandAndRegistActivity.this, "regist_db");
                                SQLiteDatabase db = helper.getWritableDatabase();
                                int delete = db.delete("user", "name=?", new String[]{list.get(position)});
                                Log.e(TAG, "onClick: delete" + delete + "list.get(position)" + list.get(position));
                                db.close();
                                popupWindow.dismiss();

                            }
                        }).show();
//                userNameAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }
}
