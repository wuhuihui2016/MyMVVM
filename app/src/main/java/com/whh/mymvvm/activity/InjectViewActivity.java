package com.whh.mymvvm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.whh.mylibrary.annotation.annot.InjectView;
import com.whh.mylibrary.annotation.annot.Autowire;
import com.whh.mymvvm.base.BaseActivity;
import com.whh.mymvvm.bean.ParcelableBean;
import com.whh.mymvvm.bean.SerializableBean;
import com.whh.mymvvm.utils.InjectViewUtil;
import com.whh.mymvvm.R;
import com.whh.mymvvm.utils.ToastUtils;

/**
 * 仿butterknife实现绑定activity，初始化View
 * author:wuhuihui 2021.06.23
 */
public class InjectViewActivity extends BaseActivity {

    @InjectView(id = R.id.text1)
    private TextView text1;
    @InjectView(id = R.id.text2)
    private TextView text2;
    @InjectView(id = R.id.button)
    private Button button;
    @InjectView(id = R.id.tv_info)
    private TextView tv_info;

    @Autowire()
    private String name;
    @Autowire()
    private int age;
    @Autowire()
    private boolean isStudent;
    @Autowire()
    private int[] data;
    @Autowire()
    private ParcelableBean parcelableBean;
    @Autowire()
    private SerializableBean serializableBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //遍历界面中的变量(控件)，解析已注解的变量(控件)，并初始化控件
        InjectViewUtil.inject(activity);

        //使用apt自动生成的代码注入View
//        InjectViewUtil.injectApt(activity);

        text1.setText("注解text1");
        text2.setText("注解text2");
        button.setText("注解button");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(activity, "响应button点击事件！");
            }
        });

        ParcelableBean bean = getIntent().getParcelableExtra("parcelableBean");
        //接收getIntent的参数值
        InjectViewUtil.injectIntentParam(activity);
        tv_info.setText("getIntent... name=" + getIntent().getStringExtra("name")
                + "\ngetIntent... parcelableBean=" + bean.name
                + "\n\ninjectIntentParam... name=" + name + ", age=" + age + ", isStudent=" + isStudent
                + "\ninjectIntentParam... data[5]=" + data[5]
                + "\ninjectIntentParam... parcelableBean=" + parcelableBean.name
                + "\ninjectIntentParam... serializableBean=" + serializableBean.name);

        //注解方法和注解方法的参数 20210625
        InjectViewUtil.annoMethod("date","20210625");
        InjectViewUtil.ergodicAnno();; //遍历方法上的所有注解及其参数
        
        /*遍历注解方法和注解方法的参数输出结果：
        System.out: annoMethod...date  20210625
        System.out: ergodicAnno...annotation=@com.whh.mymvvm.utils.InjectViewUtil$AnnoMethod1(value=AnnoMethod1)
        System.out: ergodicAnno...annotation=@com.whh.mymvvm.utils.InjectViewUtil$AnnoMethod2(value=AnnoMethod2)
        System.out: ergodicAnno...parameterAnnotations.length=2
        System.out: ergodicAnno...parameter=@com.whh.mymvvm.utils.InjectViewUtil$AnnoMethod3(key=33, value=)
        System.out: ergodicAnno...parameter=@com.whh.mymvvm.utils.InjectViewUtil$AnnoMethod3(key=, value=333)*/
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_inject;
    }
}
