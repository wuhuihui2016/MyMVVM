package com.whh.material.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.whh.material.databinding.ActivityCoordinatorMainBinding;

/**
 * NestedScrollView 嵌套滑动3
 * author:wuhuihui 2021.09.08
 */
public class CoordinatorMainActivity extends AppCompatActivity {

    private ActivityCoordinatorMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoordinatorMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnDemo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CoordinatorMainActivity.this, Demo01Activity.class));
            }
        });

        binding.btnDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CoordinatorMainActivity.this, Demo02Activity.class));
            }
        });
    }
}