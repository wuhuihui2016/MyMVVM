package com.whh.mymvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 用于 ViewPager2 + Frament 切换时时数据更新
 * author:wuhuihui 2021.06.28
 */
public class IndexViewModel extends ViewModel {
    private MutableLiveData<Integer> index = new MutableLiveData<>();

    public void setIndex(Integer integer) {
        this.index.setValue(integer);
    }

    public MutableLiveData<Integer> getIndex() {
        return index;
    }
}