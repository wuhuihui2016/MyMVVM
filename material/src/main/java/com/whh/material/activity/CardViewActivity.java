package com.whh.material.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.whh.material.R;
import com.whh.material.databinding.ActivityCardViewBinding;
import com.whh.material.databinding.ActivityNestScrollBinding;
import com.whh.material.databinding.ItemLayoutBinding;

/**
 * CardView 使得 itemLayout 显示为卡片样式
 *
 * 卡片式设计（Cards），Cards拥有自己独特的UI特 征,CardView 继承自FrameLayout类，并且可以设置圆角和阴影，使得控件具有立体性，也可以包含其
 * 他的布局容器和控件
 * card_view:cardCornerRadius 设置角半径
 * CardView.setRadius 要在代码中设置角半径，请使用
 * card_view:cardBackgroundColor 设置卡片的背景颜色
 * card_view:cardElevation 设置Z轴阴影高度
 * card_view:cardMaxElevation 设置Z轴阴影最大高度
 * card_view:cardUseCompatPadding 设置内边距
 * card_view:cardPreventCornerOverlap 在v20和之前的版本中添加内边距，这个属性是为了防止
 * android:foreground:设置水波纹效果，也可自定义
 * 卡片内容和边角的重叠
 *
 * author:wuhuihui 2021.09.08
 */
public class CardViewActivity extends AppCompatActivity {

    private ActivityCardViewBinding binding;
    private ItemLayoutBinding itemLayoutBinding,itemLayoutBinding1,itemLayoutBinding2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardViewBinding.inflate(getLayoutInflater());
        itemLayoutBinding = ItemLayoutBinding.inflate(getLayoutInflater(),binding.cardview,true);
        itemLayoutBinding1 = ItemLayoutBinding.inflate(getLayoutInflater(),binding.cardview1,true);
        itemLayoutBinding2 = ItemLayoutBinding.inflate(getLayoutInflater(),binding.cardview2,true);
        setContentView(binding.getRoot());

        itemLayoutBinding.ivPortrait.setImageDrawable(getDrawable(R.drawable.xiaoxin));
        itemLayoutBinding.tvMotto.setText("Hi,美女，喜欢吃青椒吗？");
        itemLayoutBinding.tvNickname.setText("蜡笔小新");

        itemLayoutBinding1.ivPortrait.setImageDrawable(getDrawable(R.drawable.mingren));
        itemLayoutBinding1.tvMotto.setText("我是要成为火影的男人！！！");
        itemLayoutBinding1.tvNickname.setText("鸣人");

        itemLayoutBinding2.ivPortrait.setImageDrawable(getDrawable(R.drawable.liudao));
        itemLayoutBinding2.tvMotto.setText("触碰万物之理，能控制森罗万象");
        itemLayoutBinding2.tvNickname.setText("六道仙人");


    }
}