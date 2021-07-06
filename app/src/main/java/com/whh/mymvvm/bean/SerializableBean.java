package com.whh.mymvvm.bean;

import java.io.Serializable;

/**
 * author:wuhuihui 2021.06.23
 */
public class SerializableBean implements Serializable {

    public String name;

    public SerializableBean(String name) {
        this.name = name;
    }
}
