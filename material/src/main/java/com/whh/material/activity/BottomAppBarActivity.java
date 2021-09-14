package com.whh.material.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.whh.material.databinding.ActivityBottomAppBarBinding;

/**
 *  CoordinatorLayout、BottomAppBar、FloatingActionButton
 *  author:wuhuihui 2021.09.08
 */
public class BottomAppBarActivity extends AppCompatActivity {

    private ActivityBottomAppBarBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomAppBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
