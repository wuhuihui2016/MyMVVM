package com.whh.seriorui.conflictview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 自定义 SwipeRefreshLayout，解决事件冲突示例
 * author:wuhuihui 2021.09.06
 */
public class CustomSRL extends SwipeRefreshLayout {
    public CustomSRL(Context context) {
        super(context);
    }

    public CustomSRL(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            super.onInterceptTouchEvent(ev);
            return false;
        }
        return true;
    }
}
