package com.whh.mymvvm.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.whh.mymvvm.R;
import com.whh.mymvvm.adapter.UserAdapter;
import com.whh.mymvvm.base.BaseActivity;
import com.whh.mymvvm.bean.User;
import com.whh.mymvvm.databinding.ActivityListBinding;
import com.whh.mymvvm.utils.ContantUtils;
import com.whh.mymvvm.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * author:wuhuihui 2021.05.19
 * DataBing + RecyclerView + RecyclerView.Adapter
 */
public class ListActivity extends BaseActivity {

    private ActivityListBinding activityListBinding;
    private XRecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListBinding = (ActivityListBinding) baseBinding;
        initRecyclerView();
        testData(); //本地测试数据
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_list;
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {

        recyclerView = activityListBinding.recyclerView;
        
        //设置列表滑动方向和间隔线
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 3, getResources().getColor(R.color.divide_color)));

        //设置上拉刷新下拉加载更多样式
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotate); //设置下拉刷新的样式
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate); //设置上拉加载更多的样式
        recyclerView.setArrowImageView(R.drawable.pull_down_arrow);

        //设置适配器
        userAdapter = new UserAdapter(context);
        activityListBinding.setAdapter(userAdapter); //绑定adapter, 等同于recyclerView.setAdapter(userAdapter);
    }

    /**
     * 本地测试数据
     */
    private void testData() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User("姓名：" + i, i, ContantUtils.defultPhoto));
        }

        userAdapter.updateAll(users);
        activityListBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAdapter.updateItem(new User("whh1", 18));
            }
        });
    }

}
