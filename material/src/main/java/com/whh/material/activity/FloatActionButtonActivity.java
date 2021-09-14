package com.whh.material.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.whh.material.databinding.ActivityFloatActionButtonBinding;

/**
 * 1.FloatingActionButton 悬浮按钮实现
 * android:src 设置相应图片
 * app:backgroundTint 设置背景颜色
 * app:borderWidth 设置边界的宽度。如果不设置0dp，那么在4.1的sdk上FAB会显示为正方形，而
 * 且在5.0以后的sdk没有阴影效果。
 * app:elevation 设置阴影效果
 * app:pressedTranslationZ 按下时的阴影效果
 * app:fabSize 设置尺寸normal对应56dp，mini对应40dp
 * app:layout_anchor 设置锚点，相对于那个控件作为参考
 * app:layout_anchorGravity 设置相对锚点的位置 top/bottom之类的参数
 * app:rippleColor 设置点击之后的涟漪颜色
 *
 * 2.CoordinatorLayout
 *
 * 3.Snackbar可替代Toast的工具[https://www.jianshu.com/p/faa6cc49f92f]
 * Snackbar就是一个类似Toast的快速弹出消息提示的控件，手机上显示在底部，大屏幕设备显示在左
 * 侧。但是在显示上比Toast丰富，也提供了于用户交互的接口
 *
 * author:wuhuihui 2021.09.08
 */
public class FloatActionButtonActivity extends AppCompatActivity {

    private ActivityFloatActionButtonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFloatActionButtonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Hello floatingactionbutton...",Snackbar.LENGTH_LONG)
                        .setAction("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(FloatActionButtonActivity.this, "取消", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
    }
}