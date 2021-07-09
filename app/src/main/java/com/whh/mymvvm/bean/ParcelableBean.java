package com.whh.mymvvm.bean;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable 的使用
 * writeToParcel：序列化过程
 * Creator 与 protected ParcelableBean(Parcel in) 配合实现反序列化，转换为对象。
 * <p>
 * author:wuhuihui 2021.07.09
 */
public class ParcelableBean implements Parcelable {

    public String name;

    public ParcelableBean(String name) {
        this.name = name;
    }

    protected ParcelableBean(Parcel in) {
        name = in.readString(); //【必须read】
    }

    /**
     * 反序列化
     */
    public static final Creator<ParcelableBean> CREATOR = new Creator<ParcelableBean>() {
        @Override
        public ParcelableBean createFromParcel(Parcel in) {
            return new ParcelableBean(in);
        }

        @Override
        public ParcelableBean[] newArray(int size) {
            return new ParcelableBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 序列化过程
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name); //【必须write】
    }

    public static void main(String[] args) {
        ParcelableBean bean = new ParcelableBean("whh");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ParcelableBean", bean);
        intent.putExtras(bundle);

        System.out.println("getParcelableExtra==>" + intent.getParcelableExtra("ParcelableBean"));
    }
}
