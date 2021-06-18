package com.whh.mymvvm.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Student实体类，如果没有设置表明，默认以类名作为表名
 * author:wuhuihui 2021.06.17
 */
@Entity
public class Student {

    @PrimaryKey(autoGenerate = true)
    public int sId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "age")
    public int age;

    public int getId() {
        return sId;
    }

    public void setId(int sId) {
        this.sId = sId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

