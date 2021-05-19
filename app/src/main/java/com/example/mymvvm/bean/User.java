package com.example.mymvvm.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseObservable {

    public static String defultName = "defult";
    public static String defultPhoto = "https://img2.baidu.com/it/u=1373560079,871367259&fm=26&fmt=auto&gp=0.jpg";

    public ObservableField<String> name = new ObservableField<>();
    public ObservableInt age = new ObservableInt();
    public ObservableField<String> photo = new ObservableField<>();

    public User(String name, int age) {
        this.name.set(name);
        this.age.set(age);
    }

    public User(String name, int age, String photo) {
        this.name.set(name);
        this.age.set(age);
        this.photo.set(photo);
    }

}
