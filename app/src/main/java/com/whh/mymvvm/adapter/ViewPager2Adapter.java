package com.whh.mymvvm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.whh.mymvvm.R;
import com.whh.mymvvm.base.BaseAdapter;
import com.whh.mymvvm.base.BaseViewHolder;
import com.whh.mymvvm.databinding.ItemPageBinding;

import java.util.Arrays;

/**
 * ViewPager2的适配器
 */
public class ViewPager2Adapter extends BaseAdapter {

    public String[] colors = {"#CCFF99", "#41F1E5", "#8D41F1"};

    public ViewPager2Adapter(Context context) {
        super(context);
        mDatas = Arrays.asList(colors);
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(
                inflater, R.layout.item_page, parent, false);
        return new BaseViewHolder(dataBinding); //获取viewHolder控件
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder viewHolder, int position) {
        //强转成 ItemPageBinding，获取其中的子控件
        ItemPageBinding binding = (ItemPageBinding) ((BaseViewHolder) viewHolder).getBinding();
        binding.pageItemTv.setText("" + position);
        binding.pageItemTv.setBackgroundColor(Color.parseColor((String) mDatas.get(position)));
        binding.executePendingBindings(); //强制绑定，防止列表滑动时闪烁
    }

}