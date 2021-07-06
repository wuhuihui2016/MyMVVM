package com.whh.mymvvm.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 使用注解+反射+intent传输数据时，Parcelable类型的数据【不不不】需要特殊处理
 * author:wuhuihui 2021.06.23
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
     * 内部类需使用static
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name); //【必须write】
    }
}
