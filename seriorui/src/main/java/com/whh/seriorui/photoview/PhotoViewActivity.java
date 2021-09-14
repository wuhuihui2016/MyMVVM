package com.whh.seriorui.photoview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.whh.seriorui.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * PhotoView 库使用示例
 * author:wuhuihui 2021.09.09
 */
public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photoview);
        PhotoView photoview = (PhotoView) findViewById(R.id.photoview);

        String imgUrl = "https://upload.jianshu.io/users/upload_avatars/3013099/d27e8d3e-87ed-4c37-8c33-0bd4bde50591.jpg";
        Glide.with(this).load(imgUrl).into(photoview);
        photoview.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                Log.e("whh0909", "onPhotoTap...x:" + x + ", y:" + y);
            }
        });
    }
}
