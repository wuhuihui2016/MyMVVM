package com.example.mymvvm.activity;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;
import androidx.transition.TransitionManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymvvm.R;
import com.example.mymvvm.bean.User;
import com.example.mymvvm.databinding.MainActivityBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * databinding基本使用，数据类型绑定、集合绑定、按键事件绑定
 */
public class MainActivity extends Activity {

    private User user;
    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        setUser(); //设置基本变量显示于界面
        setCollection(); //设置集合
    }

    /**
     * bean类变量
     */
    private void setUser() {
        user = new User(null, 0);
        binding.setUser(user); //给includeUserLayout赋值
        binding.setButtonname("年龄+1");
        binding.includeItemLayout.setItemUser(new User("itemUser", 5, User.defultPhoto));
        binding.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //监听数据改变
            }
        });
        //动画设置
        binding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup viewGroup = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(viewGroup);
                return true;
            }
        });
        binding.setEventlistener(new EventListener()); //绑定点击事件
    }

    //设置点击事件
    public class EventListener {
        public void ageBtnonClik(View view) {
            user.age.set(user.age.get() + 1);
        }

        public boolean onLongClick(View view) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            return false;
        }
    }

    /**
     * 集合变量
     */
    private void setCollection(){
        //List
        List<String> strings = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            strings.add("" + i);
        }
        binding.setIndex(3);
        binding.setList(strings);

        //map
        Map<String, Integer> map = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            map.put(strings.get(i), i);
        }
        binding.setKey("5");
        binding.setMap(map);

    }
}