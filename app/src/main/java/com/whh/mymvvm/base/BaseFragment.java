package com.whh.mymvvm.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.whh.mymvvm.utils.ToastUtils;


/**
 * 研究 ViewPager2 + Fragment 的懒加载
 * 懒加载也叫延迟加载:延迟加载或符合某些条件时才加载。
 * 预加载:提前加载,当用户需要查看时可直接从本地缓存中读取。
 * author:wuhuihui 2021.06.28
 */
public class BaseFragment<B extends ViewDataBinding> extends Fragment {

    protected String tag;
    /**
     * ViewDataBinding
     */
    protected B binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getClass().getSimpleName();
        System.out.println(tag + " onCreate...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println(tag + " onCreateView...");
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println(tag + " onStart...");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println(tag + " onResume...");
        if (getArguments() != null)
            ToastUtils.show(getActivity(), getArguments().getString("item"));
    }

    /**
     * onHiddenChanged在第一次显示片段时不会被调用.只有当它改变状态.
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        System.out.println(tag + " onHiddenChanged..." + hidden);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println(tag + " onDetach...");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println(tag + " onDestroyView...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println(tag + " onDestroyView...");
    }
}