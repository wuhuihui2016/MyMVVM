package com.whh.mymvvm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.whh.mylibrary.annotation.annot.InjectView;
import com.whh.mymvvm.R;
import com.whh.mymvvm.base.BaseActivity;
import com.whh.mymvvm.utils.InjectViewUtil;
import com.whh.mymvvm.utils.ToastUtils;

/**
 * apt
 * 仿butterknife实现绑定activity，初始化View
 * https://www.jianshu.com/p/1bdbd159a7ce
 * https://github.com/gi13371/APTdemo
 * author:wuhuihui 2021.06.23
 *
 * apt生成文件未成功，界面崩溃
 */
public class BindViewActivity extends BaseActivity {

    //如果使用apt生成的View，需用public
    @InjectView(id = R.id.text1)
    public TextView text1;
    @InjectView(id = R.id.text2)
    public TextView text2;
    @InjectView(id = R.id.button)
    public Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //使用apt自动生成的代码注入View
        InjectViewUtil.bind(activity);

        text1.setText("注解text1");
        text2.setText("注解text2");
        button.setText("注解button");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(activity, "响应button点击事件！");
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_inject;
    }
}
