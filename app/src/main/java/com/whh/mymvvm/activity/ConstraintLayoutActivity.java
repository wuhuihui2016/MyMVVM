package com.whh.mymvvm.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.whh.mymvvm.R;
import com.whh.mymvvm.base.BaseActivity;
import com.whh.mymvvm.databinding.ActivityConstraintLayoutBinding;

/**
 * ConstraintLayout 练习
 * author:wuhuihui 2021.06.28
 */
public class ConstraintLayoutActivity extends BaseActivity {

    private ActivityConstraintLayoutBinding constraintLayoutBinding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        constraintLayoutBinding = (ActivityConstraintLayoutBinding) baseBinding;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_constraint_layout;
    }
}
