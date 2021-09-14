package com.whh.material.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.whh.material.databinding.ActivityNestScrollBinding;

/**
 * NestScrollActivity
 * NestedScrollView和ScrollView类似，是一个支持滚动的控件。此外，它还同时支持作
 * NestedScrollingParent或者NestedScrollingChild进行嵌套滚动操作。默认是启用嵌套滚动的
 * author:wuhuihui 2021.09.08
 */
public class NestScrollActivity extends AppCompatActivity {

    private ActivityNestScrollBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNestScrollBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}