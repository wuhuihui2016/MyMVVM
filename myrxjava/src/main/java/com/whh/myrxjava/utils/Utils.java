package com.whh.myrxjava.utils;

import android.os.Looper;

public class Utils {

    /**
     * 判断是否为 UI 线程
     * @return
     */
    public static boolean isUIThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
