package com.leyifu.makefriend.fragment.memanager;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.fragment.BaseFragment;

/**
 * Created by hahaha on 2017/3/27 0027.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout tv_me_privacy;
    private LinearLayout tv_me_setting;

    @Override
    protected void init(View view) {
        initUI(view);
        tv_me_privacy.setOnClickListener(this);
        tv_me_setting.setOnClickListener(this);
    }

    /**
     * @param view
     */
    private void initUI(View view) {
        tv_me_privacy = ((LinearLayout) view.findViewById(R.id.tv_me_privacy));
        tv_me_setting = ((LinearLayout) view.findViewById(R.id.tv_me_setting));
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_me_setting;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_me_privacy:
                Toast.makeText(getActivity(),"this is a privacy",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_me_setting:
                Toast.makeText(getActivity(),"this is a setting",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
