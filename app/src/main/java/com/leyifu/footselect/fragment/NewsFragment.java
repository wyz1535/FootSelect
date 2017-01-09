package com.leyifu.footselect.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyifu.footselect.R;

/**
 * Created by hahaha on 2017/1/5 0005.
 */
public class NewsFragment extends Fragment {

    private final String TAG = "mainactivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        View inflate = inflater.inflate(R.layout.fragment_news, container,false);
        return inflate;
    }



}
