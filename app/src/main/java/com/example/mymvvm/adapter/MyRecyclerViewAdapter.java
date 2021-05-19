package com.example.mymvvm.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymvvm.BR;
import com.example.mymvvm.R;
import com.example.mymvvm.bean.User;

import java.util.List;

public class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    protected List<User> mDatas;

    public MyRecyclerViewAdapter(List<User> datas) {
        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.layout_item, parent, false);
        return new MyViewHolder(binding); //获取viewHolder控件
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.MyViewHolder viewHolder, int position) {
        ViewDataBinding binding = ((MyViewHolder) viewHolder).getBinding();
        binding.setVariable(BR.item_user, mDatas.get(position)); //注意：在引用的layout_item.xml中定义的变量为item_user
        binding.executePendingBindings(); //强制绑定
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void updateItem(User user) {
        mDatas.add(user);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

    }
}


