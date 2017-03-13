package com.leyifu.makefriend.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.activity.LandAndRegistActivity;

/**
 * Created by hahaha on 2017/1/5 0005.
 */
public class NewsFragment extends BaseFragment {

    private final String TAG = "mainactivity";

    private ImageView ivNewsTest;

    @Override
    protected void init(View view) {

        ivNewsTest = (ImageView)view.findViewById(R.id.iv_news_test);

        ivNewsTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LandAndRegistActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_news;
    }
}
