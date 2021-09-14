package com.whh.material.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.whh.material.databinding.ActivityMaterialButtonBinding;

/**
 * MaterialButton 实现
 * author:wuhuihui 2021.09.08
 */
public class MaterialButtonActivity extends AppCompatActivity {

    private ActivityMaterialButtonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMaterialButtonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.materialbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.materialbtn.setEnabled(!binding.materialbtn.isEnabled());
            }
        });
    }
}