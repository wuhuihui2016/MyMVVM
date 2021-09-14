package com.whh.seriorui.textdraw.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.whh.seriorui.R;
import com.whh.seriorui.textdraw.view.SimpleColorChangeTextView;

/**
 * 绘制文本实现渐变效果
 * author:wuhuihui 2021.09.07
 */
public class SimpleActivity extends AppCompatActivity {

    SimpleColorChangeTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        textView = findViewById(R.id.color_change_textview);

        //属性动画
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofFloat(textView, "percent", 0, 1)
                        .setDuration(5000).start();
            }
        }, 2000);
    }

}
