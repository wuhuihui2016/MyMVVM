package com.whh.material.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.SeekBar;

import com.google.android.material.chip.ChipDrawable;
import com.whh.material.R;
import com.whh.material.databinding.ActivityChipsBinding;

/**
 * ChipGroup chip 切片
 * Chip代表一个小块中的复杂实体，如联系人。 它是一个圆形按钮，由一个标签，一个可选的芯片图标
 * 和一个可选的关闭图标组成。
 * author:wuhuihui 2021.09.08
 */
public class ChipsActivity extends AppCompatActivity {

    private ActivityChipsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChipsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.chip0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChipDrawable chipDrawable = ChipDrawable.createFromResource(ChipsActivity.this,R.xml.chip_test);
                chipDrawable.setBounds(0,0,chipDrawable.getIntrinsicWidth(),chipDrawable.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(chipDrawable);
                Editable text = binding.etTest.getText();
                chipDrawable.setText(text.toString());
                text.setSpan(span,0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int max = seekBar.getMax();
                double scale = (double)progress/(double)max;
                ClipDrawable drawable = (ClipDrawable) binding.ivShow.getBackground();
                drawable.setLevel((int) (10000*scale));
                binding.tvInfo.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.materialbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.materialbtn.setEnabled(!binding.materialbtn.isEnabled());
            }
        });



    }
}