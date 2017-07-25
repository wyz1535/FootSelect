package com.leyifu.makefriend.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.activity.AccountActivity;
import com.leyifu.makefriend.activity.SettingActivity;
import com.leyifu.makefriend.utils.BackgroundAlpha;
import com.leyifu.makefriend.utils.Utils;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * Created by hahaha on 2017/1/5 0005.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {


    private View view;
    private LinearLayout ll_me_account;
    private LinearLayout ll_zxing_name_card;
    private LinearLayout ll_zxing_scan;
    private LinearLayout ll_me_collect;
    private LinearLayout ll_me_setting;
    private ImageView iv_pop_zxing;

    @Override
    protected void init(View view) {
        this.view = view;
        initView();
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_me;
    }

    private void initView() {
        ll_me_account = ((LinearLayout) view.findViewById(R.id.ll_me_account));
        ll_zxing_name_card = ((LinearLayout) view.findViewById(R.id.ll_zxing_name_card));
        ll_zxing_scan = ((LinearLayout) view.findViewById(R.id.ll_zxing_scan));
        ll_me_collect = ((LinearLayout) view.findViewById(R.id.ll_me_collect));
        ll_me_setting = ((LinearLayout) view.findViewById(R.id.ll_me_setting));

        ll_me_account.setOnClickListener(this);
        ll_zxing_name_card.setOnClickListener(this);
        ll_zxing_scan.setOnClickListener(this);
        ll_me_collect.setOnClickListener(this);
        ll_me_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_me_account:
                startActivity(new Intent(view.getContext(), AccountActivity.class));
                break;
            case R.id.ll_zxing_name_card:
                showNameCard();
                break;
            case R.id.ll_zxing_scan:
                zxingScan();
                Toast.makeText(view.getContext(),"ll_zxing_scan",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_me_collect:
                Utils.startToast(view.getContext(),"ll_me_collect");
                break;
            case R.id.ll_me_setting:
                    startActivity(new Intent(view.getContext(), SettingActivity.class));
                break;
        }
    }

    private void zxingScan() {
        //android 6.0以后需要在代码中获取权限
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(view.getContext(),
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
//                        ActivityCompat.requestPermissions(null,
//                                new String[]{android.Manifest.permission.CAMERA}, 1);//REQUEST_CODE  CAMERA_OK
                Toast.makeText(view.getContext(), "请先开启权限", Toast.LENGTH_SHORT).show();
                getParentFragment().requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 1);
            } else {
                //说明已经获取到摄像头权限了 执行逻辑
                //打开扫描界面扫描条形码或二维码
//                        Activity activity = getActivity();
                Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        } else {
            //这个说明系统版本在6.0之下，不需要动态获取权限。
            //打开扫描界面扫描条形码或二维码
            Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
            startActivityForResult(openCameraIntent, 0);
        }

    }

    private void showNameCard() {
//        String contentString = qrStrEditText.getText().toString();
        //TODO 获取账号 生成账号
        String contentString = "www.baidu.com";
        if (!"".equals(contentString)) {
            //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
//                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)
            //TODO 获取图像 设置在二维码上
            Bitmap qrCodeBitmap = EncodingUtils.createQRCode(contentString, 400, 400,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)
            );
            popWinShowZXing(qrCodeBitmap);
        } else {
            Toast.makeText(view.getContext(), "网络错误，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    private void popWinShowZXing(Bitmap qrCodeBitmap) {

        View inflate = LayoutInflater.from(view.getContext()).inflate(R.layout.pop_zxing, null);
        PopupWindow popupWindow = new PopupWindow(inflate, ViewPager.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        iv_pop_zxing = ((ImageView) inflate.findViewById(R.id.iv_pop_zxing));
        iv_pop_zxing.setImageBitmap(qrCodeBitmap);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        BackgroundAlpha.backgroundAlpha(getActivity(),0.5f);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(inflate, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
               BackgroundAlpha.backgroundAlpha(getActivity(),1.0f);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(getActivity(), scanResult, Toast.LENGTH_SHORT).show();
        }
    }
}
