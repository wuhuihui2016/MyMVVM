package com.whh.mymvvm.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whh.mymvvm.R;
import com.whh.mymvvm.base.BaseFragment;

/**
 * Databinding 在 Fragment 中使用
 * author:wuhuihui 2021.06.24
 */
public class OneFragment extends BaseFragment {

    @Override
    public void onCreate(  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(  LayoutInflater inflater,   ViewGroup container,   Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_one, container, false);
        return binding.getRoot();
    }

}