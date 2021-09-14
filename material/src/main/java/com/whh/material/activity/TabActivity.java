package com.whh.material.activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.whh.material.R;
import com.whh.material.databinding.ActivityTabBinding;

/**
 * TabLayout、TabItem
 *
 * app:tabBackground    设置TableLayout的背景色
 * app:tabTextColor     设置未被选中时文字的颜色
 * app:tabSelectedTextColor    设置选中时文字的颜色
 * app:tabIndicatorColor    设置滑动条的颜色
 * app:tabTextAppearance="@android:style/TextAppearance.Large" 设置TableLayout的文本主
 * 题，无法通过textSize来设置文字大小，只能通过主题来设定
 * app:tabMode="scrollable" 设置TableLayout可滑动，当页数较多时，一个界面无法呈现所有的
 * 导航标签，此时就必须要用。
 *
 * author:wuhuihui 2021.09.08
 */
public class TabActivity extends AppCompatActivity {

    private ActivityTabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}