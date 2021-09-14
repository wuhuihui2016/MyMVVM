package com.whh.seriorui.textdraw.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.whh.seriorui.textdraw.view.TextMeasureView;

/**
 * 文字测量演示
 * author:wuhuihui 2021.09.07
 */
public class TextMeasureActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextMeasureView(this));

    }
}
