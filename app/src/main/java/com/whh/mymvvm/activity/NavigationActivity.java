package com.whh.mymvvm.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.whh.mymvvm.R;
import com.whh.mymvvm.base.BaseActivity;
import com.whh.mymvvm.databinding.ActivityNavigationBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Navigation + BottomNavigationView 实现 Fragment 切换
 * author:wuhuihui 2021.06.24-25
 */
public class NavigationActivity extends BaseActivity {

    private ActivityNavigationBinding activityNavigationBinding;
    private NavController navController; //Fragment切换器
    private BottomNavigationView navigation; //底部菜单控件
    private final List<MenuItem> items = new ArrayList<>(); //下方菜单项的集合

    //如果跳转到 Activity，从 intent.extras 获取到 bundle，如果是 Fragment，则从 arguments 获取到。
    private final Bundle bundle = new Bundle(); //传输数据

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityNavigationBinding = (ActivityNavigationBinding) baseBinding;

        //三种获取 NavController 的方法
//        NavHostFragment.findNavController(Fragment);
//        Navigation.findNavController(Activity, @IdRes int viewId);
//        Navigation.findNavController(View);
        navController = Navigation.findNavController(this, R.id.navi_fragment); //获取切换Fragment的控制器
        navController.navigate(R.id.oneFragment); //默认加载第一个Fragment

        navigation = activityNavigationBinding.navigation; //底部菜单控件
        NavigationUI.setupWithNavController(navigation, navController); //关联控制器和底部菜单控件
        //设置点击菜单事件
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                showItem(item);
                return false;
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_navigation;
    }

    /**
     * 显示某个菜单
     *
     * @param item
     */
    private void showItem(MenuItem item) {
        //其他菜单项设为未选中状态，当前菜单为选中
        items.remove(item);
        items.add(item); //保证菜单项仅添加一次
        for (MenuItem menuItem : items) {
            menuItem.setChecked(false);
        }
        item.setChecked(true);

        //点击菜单后指定切换到 Fragment
        if (item.getItemId() == R.id.navigation_home) {
            bundle.putString("item", "one");
            navController.navigate(R.id.oneFragment, bundle);
        } else if (item.getItemId() == R.id.navigation_dashboard) {
            bundle.putString("item", "two");
            navController.navigate(R.id.twoFragment, bundle);
        } else if (item.getItemId() == R.id.navigation_notifications) {
            bundle.putString("item", "three");
            navController.navigate(R.id.threeFragment, bundle);
        }
        //每个Fragment 在 mBackStack 里都拥有唯一的ID，当重复加载时不会重复生成
        System.out.println("bundle getItem=" + bundle.getString("item")
                + ", getItemId=" + navController.getCurrentDestination().getId());
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.e("whh0624", "onSupportNavigateUp...");
        return Navigation.findNavController(this, R.id.navi_fragment).navigateUp();
    }

    //菜单来回切换，形成栈堆，点击返回键，将返回上一个菜单对应的Fragment
    //但是如果在栈里会形成多个相同的Fragment，切换了N次，意味着返回键也要点多次才可结束当前Activity
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (items.size() > 1) { //返回到上一个item
            MenuItem item = items.get(items.size() - 1); //获取当前显示的item
            items.remove(item); //移除当前item
            showItem(items.get(items.size() - 1)); //显示列表里最上面的item
        } else finish(); //没有item可显示了，关闭Activity
    }
}
