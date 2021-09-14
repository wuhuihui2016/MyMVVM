package com.whh.material.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.whh.material.R;
import com.whh.material.coordinator.adapter.AuthorRecyclerAdapter;
import com.whh.material.coordinator.bean.AuthorInfo;
import com.whh.material.databinding.ActivityCoordinatorBinding;

/**
 * CoordinatorLayout：协调者布局协调布局产生联动效果
 *
 * 实现吸顶效果
 *
 * CoordinatorLayout是用来协调其子view并以触摸影响布局的形式产生动画效果的一个super-powered
 * FrameLayout，其典型的子View包括：FloatingActionButton，SnackBar。注意：CoordinatorLayout
 * 是一个顶级父View
 *
 * author:wuhuihui 2021.09.08
 */
public class CoordinatorActivity extends AppCompatActivity {

    private ActivityCoordinatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivityCoordinatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.collapsingToolbarLayout.setTitle("蜡笔小新的梦想");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(new AuthorRecyclerAdapter(AuthorInfo.createTestData()));
    }
}