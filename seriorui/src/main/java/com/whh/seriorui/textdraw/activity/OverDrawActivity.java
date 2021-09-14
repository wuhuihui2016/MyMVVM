package com.whh.seriorui.textdraw.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.whh.seriorui.textdraw.view.OverdrawView;

/**
 * 过渡绘制演示
 * author:wuhuihui 2021.09.07
 */
public class OverDrawActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new OverdrawView(this));
    }

}
