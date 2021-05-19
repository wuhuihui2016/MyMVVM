package com.example.mymvvm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mymvvm.R;
import com.example.mymvvm.adapter.MyRecyclerViewAdapter;
import com.example.mymvvm.bean.User;
import com.example.mymvvm.databinding.ActivityListBinding;
import com.example.mymvvm.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * DataBing + RecyclerView + RecyclerView.Adapter
 */
public class ListActivity extends Activity {

    private ActivityListBinding activityListBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User("姓名：" + i, i, User.defultPhoto));
        }

        activityListBinding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        activityListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityListBinding.recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 3, getResources().getColor(R.color.divide_color)));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(users);
        activityListBinding.setAdapter(adapter); //绑定adapter
        activityListBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.updateItem(new User("whh1", 18));
            }
        });
    }
}
