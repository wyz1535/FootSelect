package com.leyifu.makefriend.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hahaha on 2017/1/5 0005.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getResourcesLayout(), container,false);
        init(view);
        return view;
    }

    protected abstract void init(View view);
    public abstract int getResourcesLayout();
}
