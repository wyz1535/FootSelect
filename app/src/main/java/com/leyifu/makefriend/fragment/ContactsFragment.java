package com.leyifu.makefriend.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.activity.UserDetailActivity;
import com.leyifu.makefriend.view.CharacterParser;
import com.leyifu.makefriend.view.PinyinComparator;
import com.leyifu.makefriend.view.SideBar;
import com.leyifu.makefriend.view.SortAdapter;
import com.leyifu.makefriend.view.SortModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hahaha on 2017/1/5 0005.
 */
public class ContactsFragment extends BaseFragment implements View.OnClickListener {

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
//    private ClearEditText mClearEditText;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private View view;
    private View mHeadView;
    private LinearLayout ll_new_firend;
    private LinearLayout ll_group;
    private LinearLayout ll_pub_account;


    @Override
    protected void init(View view) {
        this.view = view;
        initView();
        setListener();
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_contacts;
    }

    public void initView() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView) view.findViewById(R.id.dialog);
        sortListView = (ListView) view.findViewById(R.id.country_lvcountry);
        //给联系人加头
        LayoutInflater mLayoutInflater = LayoutInflater.from(getActivity());
        mHeadView = mLayoutInflater.inflate(R.layout.item_contact_list_header,
                null);
        ll_new_firend = ((LinearLayout) mHeadView.findViewById(R.id.ll_new_friend));
        ll_group = ((LinearLayout) mHeadView.findViewById(R.id.ll_group));
        ll_pub_account = ((LinearLayout) mHeadView.findViewById(R.id.ll_pub_account));

        ll_new_firend.setOnClickListener(this);
        ll_group.setOnClickListener(this);
        ll_pub_account.setOnClickListener(this);

        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView.addHeaderView(mHeadView);

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //TODO  给list添加点击事件
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                if (position >= 1) {
                    Toast.makeText(view.getContext(),
                            ((SortModel) adapter.getItem(position - 1)).getName(),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), UserDetailActivity.class);
                    intent.putExtra("name", ((SortModel) adapter.getItem(position - 1)).getName());
                    startActivity(intent);
                }
            }
        });

//        sortListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.startToast(view.getContext(),"hahaha");
//
//                return true;
//            }
//        });

        SourceDateList = filledData(getResources().getStringArray(R.array.date));

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
//        adapter = new SortAdapter(this, SourceDateList);
        adapter = new SortAdapter(view.getContext(), SourceDateList);
        sortListView.setAdapter(adapter);

//        mClearEditText = (ClearEditText) view.findViewById(R.id.filter_edit);

        // 根据输入框输入值的改变来过滤搜索
//        mClearEditText.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//                filterData(s.toString());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

    }

    public void setListener() {

    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_new_friend:
                Toast.makeText(mHeadView.getContext(), "new friend", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_group:
                Toast.makeText(mHeadView.getContext(), "new ll_group", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_pub_account:
                Toast.makeText(mHeadView.getContext(), "ll_pub_account friend", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
