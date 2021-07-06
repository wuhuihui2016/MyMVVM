package com.whh.mymvvm.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whh.mymvvm.R;
import com.whh.mymvvm.base.BaseFragment;
import com.whh.mymvvm.utils.ToastUtils;

import org.jetbrains.annotations.NotNull;

/**
 * author:wuhuihui 2021.06.24
 */
public class ThreeFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_three, container, false);
        return binding.getRoot();
    }
}