package com.whh.seriorui.conflictview;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.whh.seriorui.R;

/**
 * view 的 onTouch 事件和 onClick 事件冲突
 * author:wuhuihui 2021.09.06
 */
public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Button btn = findViewById(R.id.btn);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("btn", "onTouch...."); //触发一次响应两次
                return true; //如果返回true，则 onClick 事件被拦截，不能 onClick事件
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("btn", "onClick....");
            }
        });

    }
}
