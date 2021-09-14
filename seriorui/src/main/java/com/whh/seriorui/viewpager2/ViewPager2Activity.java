package com.whh.seriorui.viewpager2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.whh.seriorui.R;
import com.whh.seriorui.viewpager2.horizontal.HorizontalActivity;
import com.whh.seriorui.viewpager2.vertical.VerticalActivity;
import com.whh.seriorui.viewpager2.withRadioGroup.RgActivity;
import com.whh.seriorui.viewpager2.withTab.TabActivity;

/**
 * https://github.com/xiangshiweiyu/ViewPagerTwoDemo/tree/master
 * ViewPager2 使用示例
 * author:wuhuihui 2021.09.07
 */
public class ViewPager2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager2);
        findViewById(R.id.btn_horizontal).setOnClickListener(this);
        findViewById(R.id.btn_vertical).setOnClickListener(this);
        findViewById(R.id.btn_rg).setOnClickListener(this);
        findViewById(R.id.btn_tab).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_horizontal: //
                startActivity(new Intent(this, HorizontalActivity.class));
                break;
            case R.id.btn_vertical:
                startActivity(new Intent(this, VerticalActivity.class));
                break;
            case R.id.btn_tab:
                startActivity(new Intent(this, TabActivity.class));
                break;
            case R.id.btn_rg:
                startActivity(new Intent(this, RgActivity.class));
                break;
        }

    }
}
