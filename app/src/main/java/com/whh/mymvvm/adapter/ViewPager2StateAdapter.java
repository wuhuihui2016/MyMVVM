package com.whh.mymvvm.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * ViewPager2的适配器
 */
public class ViewPager2StateAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragmentList;

    public ViewPager2StateAdapter(FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    /**
     * 添加
     *
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    /**
     * 移除
     */
    public void removeFragment() {
        if (fragmentList.size() > 0) {
            fragmentList.remove(fragmentList.get(fragmentList.size() - 1));
        }
        notifyDataSetChanged();
    }


}
