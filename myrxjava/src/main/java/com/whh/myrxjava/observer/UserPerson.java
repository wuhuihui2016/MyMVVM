package com.whh.myrxjava.observer;

// 观察者 实现
public class UserPerson implements Observer {

    private final String name;
    private String message;

    public UserPerson(String name) {
        this.name = name;
    }

    @Override
    public void update(Object value) {
        this.message = (String) value;

        // 读取消息
        readMessage();
    }

    private void readMessage() {
        System.out.println(name + " 收到了推送消息：" + message);
    }
}
