package com.leyifu.makefriend.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyifu.makefriend.R;
import com.leyifu.makefriend.utils.BackgroundAlpha;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AccountActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AppCompatActivity";
    private static final int TAKE_PHONE = 1;
    private static final int CHOOSE_ALBUM = 2;
    private View popView;
    private ImageView iv_me_abck;
    private RelativeLayout rl_me_account_head;
    private RelativeLayout rl_me_account_nick_name;
    private RelativeLayout rl_me_account_number;
    private TextView tv_me_account_photo;
    private TextView tv_me_account_native;
    private TextView tv_me_account_canal;
    private PopupWindow popupWindow;
    private ImageView iv_me_account_photo;
    private Uri imageUri;
    private String imagePath;
    private TextView tv_me_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initUI();

    }

    private void initUI() {
        iv_me_abck = ((ImageView) findViewById(R.id.iv_me_back));
        rl_me_account_head = (RelativeLayout) findViewById(R.id.rl_me_account_head);
        rl_me_account_nick_name = (RelativeLayout) findViewById(R.id.rl_me_account_nick_name);
        rl_me_account_number = (RelativeLayout) findViewById(R.id.rl_me_account_number);
        iv_me_account_photo = ((ImageView) findViewById(R.id.iv_me_account_photo));
        tv_me_title = ((TextView) findViewById(R.id.tv_me_title));
        tv_me_title.setText("个人账号");

        iv_me_abck.setOnClickListener(this);
        rl_me_account_head.setOnClickListener(this);
        rl_me_account_nick_name.setOnClickListener(this);
        rl_me_account_number.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_me_back:
                finish();
                break;
            case R.id.rl_me_account_head:
                showPopWindow();
                break;
            case R.id.rl_me_account_nick_name:
                break;
            case R.id.rl_me_account_number:
                break;
            //popupwindow view
            case R.id.tv_me_account_photo:
                takePhotos();
                popupWindow.dismiss();
                break;
            case R.id.tv_me_account_native:
                fromPhotoAlbumChoose();
                popupWindow.dismiss();
                break;
            case R.id.tv_me_account_canal:
                popupWindow.dismiss();
                break;
        }
    }

    private void fromPhotoAlbumChoose() {

        if (ContextCompat.checkSelfPermission(AccountActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AccountActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(AccountActivity.this, "请先开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //打开相册
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_ALBUM);
    }

    private void handeImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv_me_account_photo.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "图片不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String select) {
        //通过uri和selection来获取图片真实的路径
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, select, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(AccountActivity.this, uri)) {
            //如果是document类型的uri，则通过document id处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + documentId;
                Log.e(TAG, "documentId=" + documentId + "selection=" + selection);
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.decuments".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通的方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片的路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }


    private void takePhotos() {
        File outputImage = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(AccountActivity.this, "com.leyifu.makefriend", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHONE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    try {
                        Toast.makeText(this, "返回结果", Toast.LENGTH_SHORT).show();
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        iv_me_account_photo.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_ALBUM:
                if (Build.VERSION.SDK_INT >= 19) {
                    //在4.4系统之后 用这种方式处理图片
                    handleImageOnKitKat(data);
                } else {
                    //在4.4系统之前 用这种方式处理图片
                    handeImageBeforeKitKat(data);
                }
                break;
        }
    }

    public void showPopWindow() {

        popView = LayoutInflater.from(this).inflate(R.layout.pop_me_accont, null);
        tv_me_account_photo = (TextView) popView.findViewById(R.id.tv_me_account_photo);
        tv_me_account_native = (TextView) popView.findViewById(R.id.tv_me_account_native);
        tv_me_account_canal = (TextView) popView.findViewById(R.id.tv_me_account_canal);

        tv_me_account_photo.setOnClickListener(this);
        tv_me_account_native.setOnClickListener(this);
        tv_me_account_canal.setOnClickListener(this);

        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        BackgroundAlpha.backgroundAlpha(this, 0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BackgroundAlpha.backgroundAlpha(AccountActivity.this, 1.0f);
            }

        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0000000));
        popupWindow.showAtLocation(AccountActivity.this.findViewById(R.id.ll_me_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }
}
