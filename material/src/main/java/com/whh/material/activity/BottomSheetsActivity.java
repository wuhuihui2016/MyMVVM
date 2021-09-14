package com.whh.material.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.whh.material.R;
import com.whh.material.databinding.ActivityBottomSheetsBinding;

/**
 * NestedScrollView 支持嵌套滑动的 ScrollView
 *
 * Bottom Sheet是Design Support Library23.2 版本引入的一个类似于对话框的控件，可以暂且叫做底
 * 部弹出框吧。 Bottom Sheet中的内容默认是隐藏起来的，只显示很小一部分，可以通过在代码中设置
 * 其状态或者手势操作将其完全展开，或者完全隐藏，或者部分隐藏
 *
 * author:wuhuihui 2021.09.08
 */
public class BottomSheetsActivity extends AppCompatActivity {

    private ActivityBottomSheetsBinding binding;

    BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomSheetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        behavior = BottomSheetBehavior.from(binding.bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.i("whh","newState: " + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("whh","slideOffset: " + slideOffset);
            }
        });

        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
        binding.btnShow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(BottomSheetsActivity.this);
                View bt = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
                mBottomSheetDialog.setContentView(bt);
                mBottomSheetDialog.show();
            }
        });
    }
}