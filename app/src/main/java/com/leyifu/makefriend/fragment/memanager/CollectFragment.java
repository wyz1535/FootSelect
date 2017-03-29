package com.leyifu.makefriend.fragment.memanager;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.fragment.BaseFragment;

/**
 * Created by hahaha on 2017/3/27 0027.
 */
public class CollectFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout ll_me_collect;

    @Override
    protected void init(View view) {
        initUI(view);
        ll_me_collect.setOnClickListener(this);
    }

    private void initUI(View view) {
        ll_me_collect = ((LinearLayout) view.findViewById(R.id.ll_me_collect));
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_me_collect;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_me_collect:
                Toast.makeText(getActivity(),"this is a collect",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
