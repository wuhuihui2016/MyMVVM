package com.whh.myrxjava.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.whh.myrxjava.R;
import com.whh.myrxjava.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * RxJava 逻辑简洁、实现优雅、使用简单
 * <p>
 * mObservable.subscribe(mObserver);
 * <p>
 * subscribe() 订阅，即连接观察者 & 被观察者
 * <p>
 * public final Disposable subscribe() {}
 * // 表示观察者不对被观察者发送的事件作出任何响应（但被观察者还是可以继续发送事件）
 * <p>
 * public final void subscribe(Observer<? super T> observer) {}
 * // 表示观察者对被观察者发送的任何事件都作出响应
 * <p>
 * public final Disposable subscribe(Consumer<? super T> onNext) {}
 * // 表示观察者只对被观察者发送的Next事件作出响应
 * <p>
 * public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {}
 * // 表示观察者只对被观察者发送的Next事件 & Error事件作出响应
 * <p>
 * public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {}
 * // 表示观察者只对被观察者发送的Next事件、Error事件 & Complete事件作出响应
 * <p>
 * public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe) {}
 * // 表示观察者只对被观察者发送的Next事件、Error事件 、Complete事件 & onSubscribe事件作出响应
 * <p>
 * <p>
 * author:wuhuihui 2021.07.13
 */
public class RxJavaActivity extends AppCompatActivity {

    private Disposable mDisposable; //避免内存泄露，在页面销毁时记得切断观察者和被观察者的联系

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

//        implTest();
//        chainImpleTest();
//        receviceParm();
        receviceList();
    }

    /**
     * observable 被观察者
     * 观察者依次调用对应事件的复写方法从而响应事件
     * 从而实现被观察者调用了观察者的回调方法，由被观察者向观察者的事件传递，即观察者模式
     */
    private void implTest() {

        //观察者初始化方式一：
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                //通过 ObservableEmitter 类对象产生时间并通知观察者
                // ObservableEmitter: 事件发射器，用于定义需要发送的事件，并向观察者发送事件
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });

        //观察者初始化方式二：依次调用e.onNext("A");==>"B"==>"C"
        Observable observable2 = Observable.just("A", "B", "C");

        //观察者初始化方式三：循环数组参数调用，效果等同方式二
        String[] words = {"A", "B", "C"};
        Observable observable3 = Observable.fromArray(words);


        // 订阅（Subscribe）连接观察者和被观察者
        observable1.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                System.out.println("whh0713, onSubscribe...");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("whh0713, onNext..." + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("whh0713, onError...");
            }

            @Override
            public void onComplete() {
                System.out.println("whh0713, onComplete...");
            }
        });
    }

    /**
     * 事件流链式实现（一行代码）
     * 调用顺序：观察者.onSubscribe（）-> 被观察者.subscribe（）-> 观察者.onNext（）->观察者.onComplete()
     */
    private void chainImpleTest() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                System.out.println("whh0713-chainImpleTest, onSubscribe..." +
                        "isUIThread：" + Utils.isUIThread());
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("whh0713-chainImpleTest, onNext..." + integer +
                        "isUIThread：" + Utils.isUIThread());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("whh0713-chainImpleTest, onError..." +
                        "isUIThread：" + Utils.isUIThread());
            }

            @Override
            public void onComplete() {
                System.out.println("whh0713-chainImpleTest, onComplete...,, " +
                        "isUIThread：" + Utils.isUIThread());
            }
        });

    }

    /**
     * just 接收简单参数、 fromArray 接收数组
     * 简单实现接收字符串  new Consumer<Integer>()
     * new Observer<Integer>() 接受参数并响应onNext、onError、onComplete 等事件
     */
    private void receviceParm() {
        System.out.println("whh0713,, isUIThread：" + Utils.isUIThread());
        Observable.just("hello").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("whh0713,, isUIThread：" + Utils.isUIThread()
                        + ", accept params is .." + s);
            }
        });

        //多个同类型参数执行
        Observable.just(1, 2, 3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("whh0713,, isUIThread：" + Utils.isUIThread()
                        + ", accept params is .." + integer);
            }
        });

        //多个同类型参数执行2
        Observable.just(1, 2, 3).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // 以数组形式传输数据，并在onNext() 中输出数组元素
        Integer[] integers = {1, 2, 3, 4};
        Observable.fromArray(integers).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("whh0713---fromArray...onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("whh0713---fromArray...onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("whh0713---fromArray...onError");
            }

            @Override
            public void onComplete() {
                System.out.println("whh0713---fromArray...onComplete");
            }
        });

    }

    /**
     * fromIterable 接收List
     */
    private void receviceList() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        //lambada 表达式
        Observable.fromIterable(list).subscribe(s -> System.out.println("whh0713---fromIterable==>onNext：" + s));

    }


    @Override
    protected void onDestroy() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();

        //如果由多个 disposable，使用 CompositeDisposable 管理和中断
        CompositeDisposable disposables = new CompositeDisposable();
        disposables.add(mDisposable);
        disposables.dispose();
        disposables.clear();
        }

        super.onDestroy();
    }
}