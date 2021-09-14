package com.whh.mymvvm.activity;

import androidx.databinding.Observable;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;
import androidx.transition.TransitionManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.ViewGroup;

import com.whh.mymvvm.R;
import com.whh.mymvvm.base.BaseActivity;
import com.whh.mymvvm.bean.ParcelableBean;
import com.whh.mymvvm.bean.SerializableBean;
import com.whh.mymvvm.bean.User;
import com.whh.mymvvm.databinding.MainActivityBinding;
import com.whh.mymvvm.utils.ContantUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:wuhuihui 2021.05.19
 * databinding基本使用，数据类型绑定、集合绑定、按键事件绑定
 * https://www.jianshu.com/p/9da84f74b85c
 */
public class MainActivity extends BaseActivity {

    private User user;
    private MainActivityBinding mainActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = (MainActivityBinding) baseBinding;
        setUser(); //设置基本变量显示于界面
        setCollection(); //设置集合
    }
    @Override
    protected int getContentViewId() {
        return R.layout.main_activity;
    }

    /**
     * bean类变量
     */
    private void setUser() {
        user = new User(null, 0);
        mainActivityBinding.setUser(user); //给includeUserLayout赋值
        mainActivityBinding.setButtonname("年龄+1");
        mainActivityBinding.includeItemLayout.setItemUser(new User("itemUser", 5, ContantUtils.defultPhoto));
        mainActivityBinding.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //监听数据改变
            }
        });
        //动画设置
        mainActivityBinding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup viewGroup = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(viewGroup);
                return true;
            }
        });
        mainActivityBinding.setEventlistener(new EventListener()); //绑定点击事件
    }

    //设置点击事件
    public class EventListener {
        public void ageBtnonClik(View view) {
            user.age.set(user.age.get() + 1);
        }

        public boolean onLongClick(View view) {
            startActivity(new Intent(activity, ListActivity.class));
            return false;
        }

        //MVVM
        public void mvvmClik(View view) {
            startActivity(new Intent(activity, RealMVVMActivity.class));
        }

        //ROOM
        public void roomClik(View view) {
            startActivity(new Intent(activity, RoomActivity.class));
        }

        //injectView注解
        public void injectView(View view) {
            Intent intent = new Intent(activity, InjectViewActivity.class);
            int[] data = {0, 1, 2, 3, 4, 5, 6};
            intent.putExtra("name", "whh");
            intent.putExtra("age", 18);
            intent.putExtra("isStudent", true);
            intent.putExtra("data", data);
            Bundle bundle = new Bundle();
            bundle.putParcelable("parcelableBean", new ParcelableBean("whh-ParcelableBean"));
            intent.putExtras(bundle);
            intent.putExtra("serializableBean", new SerializableBean("whh-SerializableBean"));
            startActivity(intent);
        }

        //injectView注解(apt)
        public void bindView(View view) {
            startActivity(new Intent(activity, BindViewActivity.class));
        }

        //Navigation组件
        public void navigation(View view) {
            startActivity(new Intent(activity, NavigationActivity.class));
        }

        //Navigation组件
        public void viewPager2(View view) {
            startActivity(new Intent(activity, ViewPager2Activity.class));
        }
    }

    /**
     * 集合变量
     */
    private void setCollection() {
        //List
        List<String> strings = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            strings.add("" + i);
        }
        mainActivityBinding.setIndex(3);
        mainActivityBinding.setList(strings);

        //map
        Map<String, Integer> map = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            map.put(strings.get(i), i);
        }
        mainActivityBinding.setKey("5");
        mainActivityBinding.setMap(map);
    }

}