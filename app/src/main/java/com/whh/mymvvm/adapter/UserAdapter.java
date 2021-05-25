package com.whh.mymvvm.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.whh.mymvvm.BR;
import com.whh.mymvvm.R;
import com.whh.mymvvm.base.BaseAdapter;
import com.whh.mymvvm.base.BaseViewHolder;
import com.whh.mymvvm.bean.User;

import java.util.List;

/**
 * author:wuhuihui 2021.05.19
 * @param <T>
 */
public class UserAdapter<T> extends BaseAdapter<User, BaseViewHolder> {

    public UserAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(
                inflater, R.layout.layout_item, parent, false);
        return new BaseViewHolder(dataBinding); //获取viewHolder控件
    }

    @Override
    public void onBindVH(BaseViewHolder viewHolder, int position) {
        ViewDataBinding binding = ((BaseViewHolder) viewHolder).getBinding();
        binding.setVariable(BR.item_user, mDatas.get(position)); //注意：在引用的layout_item.xml中定义的变量为item_user
        binding.setVariable(BR.adapter, this); //如在xml已绑定，可省略此行代码
        binding.executePendingBindings(); //强制绑定，防止列表滑动时闪烁
    }

    public void updateAll(List<User> users) {
        mDatas.clear();
        mDatas.addAll(users);
        notifyDataSetChanged();
    }

    public void updateItem(User user) {
        mDatas.add(user);
        notifyDataSetChanged();
    }


}


