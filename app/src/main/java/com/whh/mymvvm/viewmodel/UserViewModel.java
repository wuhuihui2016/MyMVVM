package com.whh.mymvvm.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.whh.mymvvm.bean.JsonUser;
import com.whh.mymvvm.bean.User;
import com.whh.mymvvm.utils.ContantUtils;
import com.whh.mymvvm.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {

    //数据请求失败返回错误信息
    private final MutableLiveData<String> errorMsg = new MutableLiveData<>();

    //数据请求成功返回数据列表
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();

    private void setUsers(List<User> users) {
        this.users.setValue(users);
    }

    public MutableLiveData<List<User>> getUsers() {
        return users;
    }

    private void setErrorMsg(String errorMsg) {
        this.errorMsg.setValue(errorMsg);
    }

    public MutableLiveData<String> getErrorMsg() {
        return errorMsg;
    }

    /**
     * 网络获取数据
     */
    public void loadData(final int currPage) {
        Log.i(ContantUtils.loadDataTag, "currPage: " + currPage);
        final List<User> userList = new ArrayList<>(); //临时变量，待数据解析完毕，更新给MutableLiveData<List<User>> users
        HttpUtils.getUserData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<JsonUser>() {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull JsonUser jsonUser) {
                        //构造Adapter所需的数据源
                        for (int i = 1; i <= 10; i++) { //模拟加载10条数据(根据分页累加age)
                            User user = new User();
                            user.age.set((currPage - 1) * 10 + i);
                            user.photo.set(jsonUser.getAvatar_url());
                            user.name.set(jsonUser.getName());
                            user.description.set(jsonUser.getSubscriptions_url());
                            userList.add(user);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        Log.i(ContantUtils.loadDataTag, "onError: " + throwable.getMessage());
                        setErrorMsg("请求失败！" + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (userList.size() > 0) setUsers(userList); //直接更新数据，是累加还是重置在Activity中依据currPage处理
                        Log.i(ContantUtils.loadDataTag, "onComplete: getUsers size = " + getUsers().getValue().size());
                    }
                });
    }

}
