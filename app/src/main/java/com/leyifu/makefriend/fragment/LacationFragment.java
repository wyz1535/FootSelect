package com.leyifu.makefriend.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.leyifu.makefriend.R;

/**
 * Created by hahaha on 2017/1/5 0005.
 */
public class LacationFragment extends BaseFragment{

    private Button bt_glide_test;
    private ImageView iv_glide_test;

    String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";

    @Override
    protected void init(View view) {
        initUI(view);

        bt_glide_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(v);
            }
        });
    }

    private void loadImage(View v) {
        Glide.with(v.getContext()).load(url).into(iv_glide_test);
    }

    private void initUI(View view) {
        bt_glide_test = ((Button) view.findViewById(R.id.bt_glide_test));
        iv_glide_test = ((ImageView) view.findViewById(R.id.iv_glide_test));
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_lacation;
    }

}
