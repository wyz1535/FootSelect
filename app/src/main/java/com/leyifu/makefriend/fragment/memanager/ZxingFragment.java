package com.leyifu.makefriend.fragment.memanager;

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
import com.leyifu.makefriend.fragment.BaseFragment;
import com.leyifu.makefriend.utils.BackgroundAlpha;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * Created by hahaha on 2017/3/27 0027.
 */
public class ZxingFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout tv_zxing;
    private LinearLayout tv_zxing_name_cadr;
    private ImageView iv_pop_zxing;

    String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";

    @Override
    protected void init(View view) {
        initUI(view);

        tv_zxing.setOnClickListener(this);
        tv_zxing_name_cadr.setOnClickListener(this);
    }

    private void initUI(View view) {
        tv_zxing = ((LinearLayout) view.findViewById(R.id.tv_zxing));
        tv_zxing_name_cadr = ((LinearLayout) view.findViewById(R.id.tv_zxing_name_cadr));
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_zxing;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_zxing_name_cadr:
//                String contentString = qrStrEditText.getText().toString();
                //TODO 获取账号 生成账号
                String contentString = "www.baidu.com";
                if (!"".equals(contentString)) {
                    //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
//                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)
                    //TODO 获取图像 设置在二维码上
                    Bitmap qrCodeBitmap = EncodingUtils.createQRCode(contentString, 400, 400,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)
                    );
                    popWinShowZXing(v, qrCodeBitmap);
                } else {
                    Toast.makeText(v.getContext(), "网络错误，请稍后再试", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_zxing:
                //android 6.0以后需要在代码中获取权限
                if (Build.VERSION.SDK_INT > 22) {
                    if (ContextCompat.checkSelfPermission(v.getContext(),
                            android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //先判断有没有权限 ，没有就在这里进行权限的申请
//                        ActivityCompat.requestPermissions(null,
//                                new String[]{android.Manifest.permission.CAMERA}, 1);//REQUEST_CODE  CAMERA_OK
                        Toast.makeText(v.getContext(), "请先开启权限", Toast.LENGTH_SHORT).show();
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

                break;
        }
    }

    private void popWinShowZXing(View v, Bitmap qrCodeBitmap) {

        View inflate = LayoutInflater.from(v.getContext()).inflate(R.layout.pop_zxing, null);
        PopupWindow popupWindow = new PopupWindow(inflate, ViewPager.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        iv_pop_zxing = ((ImageView) inflate.findViewById(R.id.iv_pop_zxing));
        iv_pop_zxing.setImageBitmap(qrCodeBitmap);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        new BackgroundAlpha().backgroundAlpha(getActivity(),0.5f);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(inflate, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                new BackgroundAlpha().backgroundAlpha(getActivity(),1.0f);
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
