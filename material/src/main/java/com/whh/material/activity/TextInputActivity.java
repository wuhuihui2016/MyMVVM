package com.whh.material.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.whh.material.databinding.ActivityTextInputBinding;

/**
 * TextInputLayout 实现输入框的动画效果
 *
 * android:hint 提示文字
 * app:counterEnabled 是否添加计数功能，默认是false
 * app:counterMaxLength 最大的输入数量（如果计数显示的话，影响显示）
 * app:errorEnabled 是否有错误提示
 * app:errorTextAppearance 设置错误提示的文字样式
 * app:hintAnimationEnabled 是否设置提示文字的动画
 * app:hintEnabled 是否启动浮动标签功能，如果不启用的话，所有提示性信息都将在Edittext中显
 * 示
 * app:hintTextAppearance 设置提示性文字的样式
 * app:passwordToggleContentDescription 该功能是为Talkback或其他无障碍功能提供
 * app:passwordToggleEnabled 是否显示后面的提示图片
 * app:passwordToggleDrawable 替换后面的提示图片
 * app:passwordToggleTint 给后面的提示图片设置颜色
 * app:passwordToggleTintMode 控制密码可见开关图标的背景颜色混合模式
 * app:counterOverflowTextAppearance 设置计算器越位后的文字颜色和大小(通过style进行设置
 * 的)
 * app:counterTextAppearance 设置正常情况下的计数器文字颜色和大小(通过style进行设置的)
 *
 * author:wuhuihui 2021.09.08
 */
public class TextInputActivity extends AppCompatActivity {

    private ActivityTextInputBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.materialbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showError(binding.textInput,"错误展示！！！");
            }
        });
    }

    private void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }
}