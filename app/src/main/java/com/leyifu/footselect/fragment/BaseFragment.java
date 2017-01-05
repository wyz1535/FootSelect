package com.leyifu.footselect.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hahaha on 2017/1/5 0005.
 */
public abstract class BaseFragment extends Fragment {

    private int resourcesLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater.inflate(getResourcesLayout(), null);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public abstract int getResourcesLayout();
}
