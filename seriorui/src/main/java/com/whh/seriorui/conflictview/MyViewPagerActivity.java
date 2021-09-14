package com.whh.seriorui.conflictview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.whh.seriorui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义 ViewPager 外部拦截法解决冲突
 * author:wuhuihui 2021.09.06
 */
public class MyViewPagerActivity extends AppCompatActivity {

    private final int[] iv = new int[]{R.drawable.refresh_loading01, R.drawable.refresh_loading02, R.drawable.refresh_loading03,
            R.drawable.refresh_loading04, R.drawable.refresh_loading05, R.drawable.refresh_loading06,
            R.drawable.refresh_loading07, R.drawable.refresh_loading08, R.drawable.refresh_loading09};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myviewpager);
        MyViewPager pager = findViewById(R.id.viewpager);

        List<Map<String, Integer>> strings = new ArrayList<>();

        Map<String, Integer> map;

        for (int i = 0; i < 20; i++) {
            map = new HashMap<>();
            map.put("key", iv[i % 9]);
            strings.add(map);
        }

        MyPagerAdapter adapter = new MyPagerAdapter(this, strings);
        pager.setAdapter(adapter);
    }
}
