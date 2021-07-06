package com.whh.thread.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference
 * 使用引用类型的原子操作类
 * 修改多个变量时，可封装在实体类中
 *
 * author:wuhuihui 2021.07.05
 */
public class UseAtomicReference {

    static AtomicReference<UserInfo> atomicReference;

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("whh", 18);
        atomicReference = new AtomicReference<>(userInfo);
        System.out.println("init.." + atomicReference.get());

        UserInfo updataUser = new UserInfo("whh123", 10);
        atomicReference.compareAndSet(userInfo, updataUser);

        System.out.println("compareAndSet.." + atomicReference.get());
        System.out.println("oldData.." + userInfo);
    }

    static class UserInfo {
        private String name;
        private int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
