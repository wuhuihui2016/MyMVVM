package com.whh.mymvvm.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.whh.mymvvm.R;
import com.whh.mymvvm.adapter.UserAdapter;
import com.whh.mymvvm.base.BaseActivity;
import com.whh.mymvvm.base.BaseLoadListener;
import com.whh.mymvvm.bean.User;
import com.whh.mymvvm.databinding.ActivityListMvvmBinding;
import com.whh.mymvvm.utils.DialogUtils;
import com.whh.mymvvm.utils.ToastUtils;
import com.whh.mymvvm.viewmodel.UserViewModel;
import com.whh.mymvvm.widget.RecycleViewDivider;

import java.util.List;

/**
 * MVVM模式加载数据
 * 把数据请求放在ViewModel中MVVM将“数据模型数据双向绑定”的思想作为核心，
 * 因此在View和Model之间没有联系，通过ViewModel进行交互，而且Model和ViewModel之间的交互是双向的，
 * 因此视图的数据的变化会同时修改数据源，而数据源数据的变化也会立即反应到View上。
 * 即，ViewModel 是一个 View 信息的存储结构，ViewModel 和 View 上的信息是一一映射关系。
 *
 * 使用MVVM模式的好处
 * 低耦合。View可以独立于Model变化和修改，一个ViewModel可以绑定到不同的View上，当View变化的时候Model可以不变，当Model变化的时候View也可以不变。
 * 可重用性。可以把一些视图的逻辑放在ViewModel里面，让很多View重用这段视图逻辑。
 * 独立开发。开发人员可以专注与业务逻辑和数据的开发(ViewModel)。设计人员可以专注于界面(View)的设计。
 * 可测试性。可以针对ViewModel来对界面(View)进行测试
 *
 * AppCompatActivity + LifecycleOwner 配合使用
 */
public class RealMVVMActivity extends BaseActivity implements
        XRecyclerView.LoadingListener, BaseLoadListener<User> {

    private ActivityListMvvmBinding mvvmBinding;
    private UserViewModel userViewModel;

    private XRecyclerView recyclerView;
    private UserAdapter userAdapter;
    private int currPage = 1; //当前页数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvvmBinding = (ActivityListMvvmBinding) baseBinding;
        initRecyclerView();
        initMVVM();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_list_mvvm;
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {

        recyclerView = mvvmBinding.recyclerView;

        //设置列表滑动方向和间隔线
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 3, getResources().getColor(R.color.divide_color)));
        recyclerView.setLoadingListener(this); //设置加载事件

        //设置适配器
        userAdapter = new UserAdapter(this);
        mvvmBinding.setAdapter(userAdapter); //绑定adapter, 等同于recyclerView.setAdapter(userAdapter);
    }

    /**
     * MVVM更新数据
     */
    private void initMVVM() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        //监听列表数据的更新
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                RealMVVMActivity.this.loadSuccess(users);
                RealMVVMActivity.this.loadComplete();
            }
        });

        //监听数据请求失败原因
        userViewModel.getErrorMsg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                RealMVVMActivity.this.loadFailure(s);
            }
        });

        //开始请求数据
        RealMVVMActivity.this.loadStart();
        userViewModel.loadData(currPage);
    }


    @Override
    public void loadStart() {
        if (currPage == 1) { //第一次加载数据
            DialogUtils.getInstance().show(this, "加载中...");
        }
    }

    @Override
    public void loadComplete() {
        DialogUtils.getInstance().close();
        recyclerView.refreshComplete(); //结束刷新
        recyclerView.loadMoreComplete(); //结束加载
    }

    @Override
    public void loadSuccess(List<User> list) {
        if (currPage > 1) { //上拉加载的数据
            userAdapter.loadDataMore(list);
        } else { //第一次加载或者下拉刷新的数据
            userAdapter.loadDataRefresh(list);
        }
    }

    /**
     * 数据请求失败
     * @param message
     */
    @Override
    public void loadFailure(String message) {
        loadComplete();
        ToastUtils.show(this, message);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        currPage = 1;
        userViewModel.loadData(currPage);
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        userViewModel.loadData(++currPage);
    }

}
