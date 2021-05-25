package com.whh.mymvvm.base;

import java.util.List;

/**
 * 数据加载监听
 * author:wuhuihui 2021.05.20
 * @param <T>
 */
public interface BaseLoadListener<T> {
    /**
     * 加载数据成功
     *
     * @param list
     */
    void loadSuccess(List<T> list);

    /**
     * 加载失败
     *
     * @param message
     */
    void loadFailure(String message);

    /**
     * 开始加载
     */
    void loadStart();

    /**
     * 加载结束
     */
    void loadComplete();
}
