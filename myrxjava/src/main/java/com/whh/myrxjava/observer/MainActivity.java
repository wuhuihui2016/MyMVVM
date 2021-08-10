package com.whh.myrxjava.observer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * TODO 主要是标准观察者模式
 * <p>
 * todo# 使用场景例子
 * 有一个微信公众号服务，不定时发布一些消息，关注公众号就可以收到推送消息，取消关注就收不到推送消息。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test();
    }

    public static void test() {
        // 编辑部 编辑好的文案内容
        String msg = "重大消息，明天开始放假！";

        // 创建一个微信公众号服务（被观察者）
        Observable server = new WechatServerObservable();

        // 创建 用户 （观察者）  多个
        Observer user1 = new UserPerson("111");
        Observer user2 = new UserPerson("222");
        Observer user3 = new UserPerson("333");

        // 订阅  注意：这里的订阅还是 被观察者.订阅(观察者)  关注
        server.addObserver(user1);
        server.addObserver(user2);
        server.addObserver(user3);

        // 微信平台 发生了 改变
        server.pushMessage(msg);

        // user2 取消了关注
        System.out.println("========================================");
        server.removeObserver(user2);

        // 再次微信平台 发生了 改变
        server.pushMessage(msg);
    }
}
