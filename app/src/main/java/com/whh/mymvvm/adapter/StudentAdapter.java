package com.whh.mymvvm.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.whh.mymvvm.BR;
import com.whh.mymvvm.R;
import com.whh.mymvvm.base.BaseAdapter;
import com.whh.mymvvm.base.BaseViewHolder;
import com.whh.mymvvm.room.Student;

import java.util.List;

/**
 * 数据显示
 * author:wuhuihui 2021.06.17
 * @param <T>
 */
public class StudentAdapter<T> extends BaseAdapter<Student, BaseViewHolder> {

    public StudentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(
                inflater, R.layout.layout_item_student, parent, false);
        return new BaseViewHolder(dataBinding); //获取viewHolder控件
    }

    @Override
    public void onBindVH(BaseViewHolder viewHolder, int position) {
        ViewDataBinding binding = ((BaseViewHolder) viewHolder).getBinding();
        binding.setVariable(BR.item_student, mDatas.get(position)); //BR找不到问题解决：为代码问题，StudentDao接口类声明的方法没有实现
        binding.setVariable(BR.adapter_student, this);
        binding.executePendingBindings(); //强制绑定，防止列表滑动时闪烁
    }

    public void updateAll(List<Student> Students) {
        mDatas.clear();
        mDatas.addAll(Students);
        notifyDataSetChanged();
    }

}


