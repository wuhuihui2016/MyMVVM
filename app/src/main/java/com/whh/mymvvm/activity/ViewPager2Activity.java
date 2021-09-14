package com.whh.mymvvm.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.whh.mymvvm.R;
import com.whh.mymvvm.adapter.ViewPager2Adapter;
import com.whh.mymvvm.adapter.ViewPager2StateAdapter;
import com.whh.mymvvm.base.BaseActivity;
import com.whh.mymvvm.databinding.ActivityViewpager2Binding;
import com.whh.mymvvm.fragment.OneFragment;
import com.whh.mymvvm.fragment.OtherFragment;
import com.whh.mymvvm.fragment.ThreeFragment;
import com.whh.mymvvm.fragment.TwoFragment;
import com.whh.mymvvm.viewmodel.IndexViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * author:wuhuihui 2021.06.25
 * ViewPager2 (06.25)
 * ViewModel + BottomNavigationView (06.28)
 * <p>
 * 与 JetPack Navigation 相比，来回切换 Fragment，
 * 不会出现重复堆积栈问题，点击返回按钮，直接 finish 了 Activity
 *
 * 探究 ViewPager2 的强大之处 (6.30)
 * 在 setAdapter 时，可以有两种方式
 * 方式一：作为 RecyclerView 来处理，加载 layout 来显示 UI，adapter 继承自 RecyclerView.Adapter
 * 方法二：加载已有不同的 Fragment，adapter 继承自 FragmentStateAdapter(本页面默认使用方式二)
 */
public class ViewPager2Activity extends BaseActivity {

    private ActivityViewpager2Binding activityViewpager2Binding;
    private ViewPager2 viewPager2;
    private final List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager2StateAdapter adapter;
    private final Bundle bundle = new Bundle();
    private IndexViewModel viewModel;
    private int index;
    private BottomNavigationView navigation; //菜单控件
    private final List<MenuItem> items = new ArrayList<>(); //菜单项集合

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityViewpager2Binding = (ActivityViewpager2Binding) baseBinding;
        viewPager2 = activityViewpager2Binding.viewPager2;

        //setAdapter 方式，二选一
        //setRecyclerViewAdapter
//        viewPager2.setAdapter(new ViewPager2Adapter(context));

        //setStateAdapter
        setStateAdapter();

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_viewpager2;
    }

    /**
     * 加载 Fragment
     */
    private void setStateAdapter() {

        navigation = activityViewpager2Binding.navigation; //底部菜单控件
        //设置点击菜单事件
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                showItem(item);
                return false;
            }
        });

        viewModel = new ViewModelProvider(this).get(IndexViewModel.class);
        //监听index的更新
        viewModel.getIndex().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                index = integer;
            }
        });

        fragmentList.add(new OneFragment());
        fragmentList.add(new TwoFragment());
        fragmentList.add(new ThreeFragment());

        adapter = new ViewPager2StateAdapter(this, fragmentList);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(3);

        pageChangeListen();
        setEvent();
    }

    /**
     * 显示某个菜单
     *
     * @param item
     */
    private void showItem(MenuItem item) {
        //其他菜单项设为未选中状态，当前菜单为选中
        if (!items.contains(item)) {
            items.add(item); //保证菜单项仅添加一次
        }
        for (MenuItem menuItem : items) {
            menuItem.setChecked(false);
        }
        item.setChecked(true);

        //点击菜单后指定切换到 Fragment
        if (item.getItemId() == R.id.navigation_home) {
            bundle.putString("item", "one");
            viewPager2.setCurrentItem(0);
        } else if (item.getItemId() == R.id.navigation_dashboard) {
            bundle.putString("item", "two");
            viewPager2.setCurrentItem(1);
        } else if (item.getItemId() == R.id.navigation_notifications) {
            bundle.putString("item", "three");
            viewPager2.setCurrentItem(2);
        }
    }

    /**
     * 事件处理
     */
    private void setEvent() {

        activityViewpager2Binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addFragment(new OtherFragment());
            }
        });
        activityViewpager2Binding.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeFragment();
            }
        });
    }

    private void pageChangeListen() {
        //viewPager2监听滑动事件
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                System.out.println("viewPager2 onPageScrolled...");
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                System.out.println("viewPager2 onPageSelected...");
                index++;
                viewModel.setIndex(Integer.valueOf(index)); //当滑动时 idnex ++
                //viewModel.setIndex(Integer.valueOf(index ++)); //无效引用！！！
                if (fragmentList.size() > 0) {
                    bundle.putString("item", index + ""); //当选定时获取index
                    fragmentList.get(position).setArguments(bundle);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                System.out.println("viewPager2 onPageScrollStateChanged...");
            }
        });
    }


}
