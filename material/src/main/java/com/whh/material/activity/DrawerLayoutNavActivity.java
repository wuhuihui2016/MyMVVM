package com.whh.material.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.whh.material.databinding.ActivityCloudMusicBinding;

/**
 * NavigationView 导航菜单
 *
 * DrawerLayout + NavigationView 实现抽屉布局展示抽屉菜单
 * DrawerLayout 实现了侧滑菜单效果的控件，
 *
 * NavigationView
 * 通常放在DrawerLayout中，可以实现侧滑效果的UI。DrawerLayout布局可以有3个子布局，第一个布
 * 局必须是主界面且不可以不写，其他2个子布局就是左、右两个侧滑布局，左右两个侧滑布局可以只写
 * 其中一个
 * android:layout_gravity 值为start则是从左侧滑出，值为end则是从右侧滑出
 * app:menu NavigationView是通过菜单形式在布局中放置元素的，值为自己创建的菜单文件
 * app:headerLayout 给NavigationView设置头文件
 * app:itemTextColor 设置菜单文字的颜色
 * app:itemIconTint 设置菜单图标的颜色
 * app:itemBackground 设置菜单背景的颜色
 *
 * [https://blog.csdn.net/yechaoa/article/details/91452474]
 * author:wuhuihui 2021.09.08
 */
@SuppressLint("WrongConstant")
public class DrawerLayoutNavActivity extends AppCompatActivity {

    private ActivityCloudMusicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        binding = ActivityCloudMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnOpenLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(Gravity.START);
            }
        });
        binding.btnOpenRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(Gravity.END);
            }
        });

        binding.btnCloseRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.closeDrawer(Gravity.END);
                //binding.drawerLayout.closeDrawers(); //关闭所有
            }
        });

    }
}