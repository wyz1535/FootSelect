package com.leyifu.makefriend.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leyifu.makefriend.R;

import java.util.List;

/**
 * Created by hahaha on 2017/3/14 0014.
 */
public class UserNameAdapter extends BaseAdapter {

    private Context context;
    private List<String> nameMap;

    public UserNameAdapter(Context context, List<String> nameMap) {
        this.context = context;
        this.nameMap = nameMap;
    }

    @Override
    public int getCount() {
        return 3;
//        return nameMap == null ? 0 : nameMap.size();
    }

    @Override
    public Object getItem(int position) {
        return nameMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.list_item, null);
        }

        ViewHolder viewHolder = ViewHolder.getViewHolder(convertView);
        viewHolder.tv_pop_item.setText(nameMap.get(position));
        return convertView;
    }

    private static class ViewHolder {

        TextView tv_pop_item;

        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder viewHolder = ((ViewHolder) convertView.getTag());
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
                viewHolder.tv_pop_item = ((TextView) convertView.findViewById(R.id.tv_pop_item));
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }

    }

}
