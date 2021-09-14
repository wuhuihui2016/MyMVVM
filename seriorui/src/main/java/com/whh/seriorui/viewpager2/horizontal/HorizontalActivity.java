package com.whh.seriorui.viewpager2.horizontal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.whh.seriorui.R;

/**
 * https://github.com/xiangshiweiyu/ViewPagerTwoDemo/tree/master
 * ViewPager2 纵向使用示例
 * author:wuhuihui 2021.09.07
 */
public class HorizontalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        ViewPager2 viewPager2 = findViewById(R.id.vp_h);
        HorizontalVpAdapter adapter = new HorizontalVpAdapter(this);
        viewPager2.setAdapter(adapter);
    }
}
