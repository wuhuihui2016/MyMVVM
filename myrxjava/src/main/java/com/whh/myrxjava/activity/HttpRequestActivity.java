package com.whh.myrxjava.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.whh.myrxjava.R;
import com.whh.myrxjava.bean.HotWord;
import com.whh.myrxjava.http.GetRequest;
import com.whh.myrxjava.bean.Gongzh;
import com.whh.myrxjava.http.RetrofitUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava线程控制 subscribeOn() & observeOn()
 * [https://blog.csdn.net/jb_home/article/details/112410741]
 * <p>
 * Schedulers.immediate()             当前线程 = 不指定线程(默认)
 * AndroidSchedulers.mainThread()     Android主线程，可操作UI
 * Schedulers.newThread()                  常规新线程，做耗时等操作
 * Schedulers.io()	                       io操作线程，网络请求、读写文件等io密集型操作
 * Schedulers.computation()	       CPU计算操作线程，大量计算操作
 * <p>
 * 若 Observable.subscribeOn() 多次指定被观察者生产事件的线程，则仅第一次指定有效，其余无效
 *   原因：线程已经执行后续指定已失效，箭已上弦！
 * 若 Observable.observeOn() 多次指定观察者接收 & 响应事件的线程，则每次指定均有效，即每指定一次，就会进行一次线程的切换
 * <p>
 * <p>
 * observeOn() 只是在收到 onNext() 等消息的时候改变了从下一个开始的操作符的线程运行环境。
 * subscribeOn() 线程切换是在 subscribe() 订阅的时候切换，他会切换他下面订阅的操作符的运行环境，因为订阅的过程是自下而上的，所以第一个出现的 subscribeOn() 操作符反而是最后一次运行的。
 * observeOn()、subscribeOn() 没有任何先后顺序的问题
 * <p>
 * author:wuhuihui 2021.07.14
 */
@SuppressLint("CheckResult")
public class HttpRequestActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_rxjava);

        testRxJavaInThread(); //在新线程中查看Observable执行的线程状态
//        requrestData(); //使用 interval 轮询请求数据
//        repeatWhenData(); //使用 repeatWhen，delay 等操作符
//        retryWhenData();
//        fixRequest(); //混合请求数据(网络请求嵌套回调)
//        checkThread();
    }

    private void testRxJavaInThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("whh0804");
                        //in thread is main
                        Log.e("whh0804", "subscribe...in thread is " + Thread.currentThread().getName());
                    }
                }).subscribeOn(AndroidSchedulers.mainThread()) //不受thread的影响
                        .observeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                //in thread is RxCachedThreadScheduler-1
                                Log.e("whh0804", "accept..." + s
                                        + ", in thread is " + Thread.currentThread().getName());
                            }
                        });
            }
        }).start();
    }

    /**
     * 轮询请求数据
     */
    private void requrestData() {
        System.out.println("whh0714---requrestData...");
        //延迟3s后发送事件，每隔3秒请求一次数据
        Observable.interval(3, 3, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("whh0714---第 " + aLong + " 次轮询");

                        GetRequest getRequest = RetrofitUtils.getRetrofit().create(GetRequest.class);
                        Observable<Gongzh> observable = getRequest.getCall();
                        observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Gongzh>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Gongzh gongzh) {
                                        System.out.println("whh0714---getRequest Gongzh=="
                                                + gongzh.getData().get(0).toString());
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                }).subscribe(); //订阅消息

    }

    // 设置变量 = 模拟轮询服务器次数
    private int i = 0;

    /**
     * 使用repeatWhen，delay等操作符
     * 实现间隔2秒发送请求
     */
    private void repeatWhenData() {

        System.out.println("whh0714---repeatWhen...");
        GetRequest getRequest = RetrofitUtils.getRetrofit().create(GetRequest.class);
        Observable<Gongzh> observable = getRequest.getCall();
        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object throwable) throws Exception {

                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                        if (i > 5) {
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(2, TimeUnit.SECONDS);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Gongzh>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Gongzh gongzh) {
                        System.out.println("whh0714---getRequest Gongzh=="
                                + gongzh.getData().get(0).toString());
                        i++;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void retryWhenData() {
        System.out.println("whh0714---retryWhenData...");

        GetRequest getRequest = RetrofitUtils.getRetrofit().create(GetRequest.class);
        Observable<Gongzh> observable = getRequest.getCall();
        observable
//                //请求失败，在2秒后重新请求
//                .repeatWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
//                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
//                            @Override
//                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
//                                return Observable.just(1).delay(2, TimeUnit.SECONDS);
//                            }
//                        });
//                    }
//                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Gongzh>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Gongzh gongzh) {
                        System.out.println("whh0714---getRequest Gongzh=="
                                + gongzh.getData().get(0).toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 混合请求数据：
     * 等待[公众号]数据请求完成后继续请求[热词]数据
     */
    private void fixRequest() {
        System.out.println("whh0714---fixRequest...");
        GetRequest getRequest = RetrofitUtils.getRetrofit().create(GetRequest.class);
        Observable<Gongzh> gongzhObservable = getRequest.getCall();
        Observable<HotWord> hotWordObservable = getRequest.getHotWord();
        gongzhObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Gongzh>() {
                    @Override
                    public void accept(Gongzh gongzh) throws Exception {
                        if (gongzh.getErrorCode() == 0) {
                            System.out.println("whh0714---getRequest Gongzh=="
                                    + gongzh.getData().get(0).toString());
                        }
                    }
                }).observeOn(Schedulers.io())
                .flatMap(new Function<Gongzh, ObservableSource<HotWord>>() {
                    @Override
                    public ObservableSource<HotWord> apply(Gongzh gongzh) throws Exception {
                        return hotWordObservable;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotWord>() {
                    @Override
                    public void accept(HotWord hotWord) throws Exception {
                        System.out.println("whh0714---getRequest hotWord=="
                                + hotWord.getData().toString());
                    }
                });
    }

    /**
     * 线程切换测试
     * Schedulers.io() ==> RxCachedThreadScheduler
     * AndroidSchedulers.mainThread() ==> main
     * Schedulers.newThread() ==> RxNewThreadScheduler
     */
    private void checkThread() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                //TODO　运行在哪个线程，由 subscribeOn 决定
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                System.out.println("whh0714---subscribeOn in thread =="
                        + Thread.currentThread().getName());
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        //TODO　运行在哪个线程，由 observeOn 决定
                        System.out.println("whh0714---checkThread in thread =="
                                + Thread.currentThread().getName() + ", accept " + integer);
                    }
                });

    }


    /**
     *
     */
    private void compose() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                //TODO　运行在哪个线程，由 subscribeOn 决定
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                System.out.println("whh0714---subscribeOn in thread =="
                        + Thread.currentThread().getName());
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        //TODO　运行在哪个线程，由 observeOn 决定
                        System.out.println("whh0714---checkThread in thread =="
                                + Thread.currentThread().getName() + ", accept " + integer);
                    }
                });

    }

}
