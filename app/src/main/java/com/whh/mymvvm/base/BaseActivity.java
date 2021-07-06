package com.whh.mymvvm.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * BaseActivity
 * 统一 DataBinding
 * author:wuhuihui 2021.06.28
 * @param <B>
 */
public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected Activity activity;
    protected Context context;

    //ViewDataBinding
    protected B baseBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        context = this;
        baseBinding = DataBindingUtil.setContentView(this, getContentViewId());
        if (baseBinding == null)
            throw new IllegalArgumentException("activty bind the layout is failed!");
    }

    protected abstract int getContentViewId();
}
