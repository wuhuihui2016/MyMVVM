package com.whh.myrxjava.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.whh.myrxjava.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 操作符
 * author:wuhuihui 2021.07.13
 */
@SuppressLint("CheckResult")
public class RxJavaOperatorActivity extends AppCompatActivity {

    private Disposable mDisposable; //避免内存泄露，在页面销毁时记得切断观察者和被观察者的联系

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

//        deferTest();
//        timerTest();
//        intervalTest();
//        intervalRangeTest();
//        rangeTest();
//        mapTest();
//        testFlatMap();
//        testBuffer();
//        testConcat();
//        mergeArray();
//        testDelayError();
//        testZip();
//        combineLatest();
//        reduce();
//        collect();
//        startWith();
//        count();
//        delay();
//        doOnNext();
        repeat();
    }

    /**
     * defer 操作符：保证在被订阅时获取到最新数据
     */
    Integer integer = 10;

    private void deferTest() {
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(integer); //在被订阅前给数据做个标记
            }
        });

        integer = 15; //更改数据

        mDisposable = observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("whh0713---deferTest...接收到最新数据：" + integer);
                //System.out: whh0713---deferTest...接收到最新数据：15
            }
        });

    }

    /**
     * timer 操作符：
     * 快速创建1个被观察者对象（Observable）
     * 发送事件的特点：延迟指定时间后，发送1个数值0（Long类型）
     * timer操作符默认运行在一个新线程上
     * 也可自定义线程调度器（第3个参数）：timer(long,TimeUnit,Scheduler)
     */
    private void timerTest() {
        System.out.println("whh0713---timer start!");
        //事件在订阅2秒后触发，第二个参数为第一个参数的单位:
        // SECONDS 秒、MICROSECONDS 微秒、MILLISECONDS 毫秒、NANOSECONDS 纳秒
        Observable.timer(2, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("whh0713---timer accept running in thread "
                                + Thread.currentThread().getName());

                        //.observeOn(AndroidSchedulers.mainThread())
                        // 订阅主线程：running in thread main

                        //.observeOn(Schedulers.io())
                        //订阅 IO 线程：running in thread RxCachedThreadScheduler-2
                    }
                });
    }

    /**
     * interval()
     * 快速创建1个被观察者对象（Observable）
     * 发送事件的特点：每隔指定时间触发事件
     * interval默认在computation调度器上执行
     * 也可自定义指定线程调度器（第3个参数）：interval(long,TimeUnit,Scheduler)
     */
    private void intervalTest() {
        //延迟3s后发送事件，每隔1秒产生1个数字（从0开始递增1，到5时停止）
        //interval(long initialDelay, long period, TimeUnit unit)
        //initialDelay 第1次延迟时间，period 间隔时间值，unit 间隔时间的单位
        System.out.println("whh0713----intervalTest==>start...");
        mDisposable = Observable.interval(3, 1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("whh0713----intervalTest==>" + aLong);
                        if (aLong == 5) { //增长到10，停止增长
                            mDisposable.dispose();
                            System.out.println("whh0713----intervalTest==>stop!!!");
                        }
                    }
                });
    }

    /**
     * intervalRange()
     * 快速创建1个被观察者对象（Observable）
     * 发送事件的特点：每隔指定时间触发事件，可指定发送的数据的数量
     */
    private void intervalRangeTest() {
        System.out.println("whh0713---intervalRange start....");
        //从3开始增长，增长5-1次，第一增长在2秒后，后面每间隔1秒增长一次
        //intervalRange(long start, long count, long initialDelay, long period, TimeUnit unit)
        //start 起始值，从这个值开始增长；count-1 增长次数；initialDelay 第一次增长事件触发的延迟事件；period 触发的时间间隔；unit 时间单位
        Observable.intervalRange(3, 5, 2, 1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("whh0713---intervalRange==>" + aLong);
                    }
                });
    }

    /**
     * range()
     * 发送的事件序列 = 从0开始、无限递增1的的整数序列
     * 作用类似于intervalRange()，但区别在于：无延迟发送事件
     * <p>
     * rangeLong() 与range()相似，但只接收 Long 类型的数据
     */
    private void rangeTest() {
        //range(final int start, final int count)
        //start 起始值；count-1 增长次数(count必须大于0，否则报异常：IllegalArgumentException)
        //从3开始增长，增长5-1次==>3....7
        System.out.println("whh0713---rangeTest==>start");
        Observable.range(3, 5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("whh0713---rangeTest==>" + integer);
                    }
                });
    }

    /**
     * map 变换操作符
     * 将 Integer 类型的数据拦截，转换成 String 类型
     */
    private void mapTest() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 3; i++) {
                    e.onNext(new Integer(i));
                }
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer + " 变身文本啦！==>" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("whh0713----从数字 " + s);
            }
        });
    }

    /**
     * flatMap() 同类型数据转换
     * 将被观察者发送的事件序列进行拆分+转换(随机无序转换)，组成新的事件序列，再发送
     * <p>
     * 原理：
     * 为事件序列中每个事件都创建一个 Observable 对象；
     * 将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
     * 将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
     * 新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
     * <p>
     * concatMap() 同 flatMap() 功能，区别在于：拆分 & 重新合并生成的事件序列 的顺序 = 被观察者旧序列生产的顺序
     */
    private void testFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 3; i++) {
                    e.onNext(new Integer(i));
                }
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add(integer + " 变身文本啦！==>" + integer);
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("whh0713----flatMap...从数字 " + s);
                //新合并生成的事件序列顺序是无序的，即与旧序列发送事件的顺序无关
            }
        });
    }

    /**
     * buffer() 缓存被观察者发送的事件
     * 应用场景？？
     */
    private void testBuffer() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2)
                // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        System.out.println("whh0713---testBuffer...");
                        for (Integer i : integers) {
                            System.out.println("whh0713---testBuffer==>" + i);
                        }
                    }
                });
        // 1，2，3 ==> 3,4,5 ==> 5
    }

    /**
     * concat()、concatArray() 组合发送数据
     * 两者区别：组合被观察者的数量，即concat()组合被观察者数量≤4个，而concatArray()则可＞4个
     */
    private void testConcat() {
        Observable.concat(
                Observable.just(1, 2),
                Observable.just(3, 4),
                Observable.just(5, 6, 7),
                Observable.just(8, 9, 10))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("whh0713---testConcat==>" + integer);
                    }
                });
        //concatArray() 可以接收4个以上的数据
//        Observable.concatArray(
//                Observable.just(1, 2),
//                Observable.just(3, 4),
//                Observable.just(5, 6, 7),
//                Observable.just(8, 9, 10),
//                Observable.just(11, 12, 13))
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        System.out.println("whh0713---testConcat==>" + integer);
//                    }
//                });
    }

    /**
     * merge()、 mergeArray() 组合多个被观察者一起发送数据，合并后 按时间线并行执行
     * 两者区别：merge()组合被观察者数量≤4个，mergeArray()则可＞4个
     * 顺序执行事件
     */
    private void mergeArray() {
        System.out.println("whh0713---mergeArray==>start");
        Observable.merge(Observable.range(0, 2), //从0开始增长，增长2-1次==>0....1
                //从3开始增长，增长5-1次，第一增长在2秒后，后面每间隔1秒增长一次 3....7
                Observable.intervalRange(3, 5, 2, 1, TimeUnit.SECONDS))
                .subscribe(new Consumer<Number>() {
                    @Override
                    public void accept(Number number) throws Exception {
                        System.out.println("whh0713---mergeArray==>" + number.intValue());
                    }
                });
    }

    /**
     * concatDelayError()、mergeDelayError()
     * 拦截后面的订阅事件
     * <p>
     * concat、concatArrayDelayError 不能同时进行！！
     */
    private void testDelayError() {
        Observable.concat(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new NullPointerException());
                    }
                }), Observable.just(3, 5)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("whh0713---TestDelayError==>concat..." + integer);
            }
        });
        //1，2事件正常发送，完成后出现onError，后面的3，5被中断不再发送

        System.out.println("whh0713---TestDelayError==>");
        Observable.concatArrayDelayError(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new NullPointerException());
                    }
                }), Observable.just(3, 5)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("whh0713---TestDelayError==>concatArrayDelayError..." + integer);
            }
        });
        //concatArrayDelayError：1，2事件正常发送，完成后出现onError，后面的3，5不受影响继续发送
    }

    /**
     * zip()
     * 合并各种类型的数据发送
     */
    private void testZip() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 1; i < 4; i++) {
                    e.onNext(i);
                    Thread.sleep(1000);
//                    e.onComplete(); //被完成中断时不再发送
                }
            }
        }).subscribeOn(Schedulers.io()); //设置被观察者1在工作线程1中工作

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                for (int i = 1; i < 4; i++) {
                    e.onNext("whh" + i);
                    Thread.sleep(1000);
                }
            }
        }).subscribeOn(Schedulers.newThread()); //设置被观察者2在工作线程2中工作

        //TODO 如果不作线程控制，则该两个被观察者会在同一个线程中工作，即发送事件存在先后顺序，而不是同时发送

        //第一个Integer是observable1的类型，第二个String是observable2的类型，第三个String是合并后的类型
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + "==>" + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("whh0713---testZip..." + s);
                //每间隔1秒输出whh0713---testZip...1==>whh1...3
            }
        });

        //TODO 如果某个 observable 没有发送事件,则会有事件的 observable 继续发送,但是不能获取合并结果
    }

    /**
     * combineLatest()  类似于 zip(),区别 :
     * Zip() = 按个数合并，即1对1合并；
     * CombineLatest() = 按时间合并，即在同一个时间点上合并
     */
    private void combineLatest() {
        Observable.combineLatest(
                Observable.just(1L, 2L, 3L), // 第1个发送数据事件的Observable
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS), // 第2个发送数据事件的Observable：从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                new BiFunction<Long, Long, String>() {
                    @Override
                    public String apply(Long o1, Long o2) throws Exception {
                        // o1 = 第1个Observable发送的最新（最后）1个数据
                        // o2 = 第2个Observable发送的每1个数据
                        // 即第1个Observable发送的最后1个数据 与 第2个Observable发送的每1个数据进行结合
                        return "o1=" + o1 + " && o2=" + o2;
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("whh0713---合并的结果是： " + s);
            }
        });
    }

    /**
     * reduce()
     * 把被观察者需要发送的事件聚合成1个事件 & 发送
     */
    private void reduce() {
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    // 在该复写方法中复写聚合的逻辑
                    @Override
                    public Integer apply(Integer s1, Integer s2) throws Exception {
                        System.out.println("whh0713---本次计算的数据是： " + s1 + " 乘 " + s2);
                        return s1 * s2;
                        // 本次聚合的逻辑是：全部数据相乘起来
                        // 原理：第1次取前2个数据相乘，之后每次获取到的数据 = 返回的数据x原始下1个数据每
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer s) throws Exception {
                System.out.println("whh0713---最终计算的结果是： " + s);
            }
        });

        //1*2=2; 2*3=6; 6*4=24
    }

    /**
     * collect() 将被观察者Observable发送的数据事件收集到一个数据结构里
     */
    private void collect() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .collect(
                        // 1. 创建数据结构（容器），用于收集被观察者发送的数据
                        new Callable<ArrayList<Integer>>() {
                            @Override
                            public ArrayList<Integer> call() throws Exception {
                                return new ArrayList<>();
                            }
                            // 2. 对发送的数据进行收集
                        }, new BiConsumer<ArrayList<Integer>, Integer>() {
                            @Override
                            public void accept(ArrayList<Integer> list, Integer integer)
                                    throws Exception {
                                // 参数说明：list = 容器，integer = 后者数据
                                list.add(integer);
                                // 对发送的数据进行收集
                            }
                        }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> s) throws Exception {
                System.out.println("whh0713---本次发送的数据是： " + s);
            }
        });
    }

    /**
     * startWith() 发送事件前追加发送事件
     * 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
     * startWithArray ==> startWith ==> just
     */
    private void startWith() {
        Observable.just(4, 5, 6)
                .startWith(0)
                .startWithArray(1, 2, 3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("whh0713---startWith： " + integer);
            }
        });
        //1,2,3,0,4,5,6
    }

    /**
     * count()
     * 统计发送事件数量
     */

    private void count() {
        Observable.just(4, 5, 6)
                .count().subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("whh0713---count()发送的事件数量 ： " + aLong);
            }
        });
    }

    /**
     * delay() 延迟发送
     * 1. 指定延迟时间 delay(long delay,TimeUnit unit) 参数1 = 时间；参数2 = 时间单位
     * 2. 指定延迟时间 & 调度器 delay(long delay,TimeUnit unit,mScheduler scheduler) 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器
     * 3. 指定延迟时间  & 错误延迟 delay(long delay,TimeUnit unit,boolean delayError) 错误延迟，即：若存在Error事件，则如常执行，执行后再抛出错误异常
     * 4. 指定延迟时间 & 调度器 & 错误延迟 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器；参数4 = 错误延迟参数
     * delay(long delay,TimeUnit unit,mScheduler scheduler,boolean delayError): 指定延迟多长时间并添加调度器，错误通知可以设置是否延迟
     */
    private void delay() {
        System.out.println("whh0713---delay...start!");
        Observable.just(1, 2, 3)
                .delay(2, TimeUnit.SECONDS) //延迟3秒发送
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("whh0713---delay==>" + integer);
                    }
                });
    }

    /**
     * doOnNext()
     */
    private void doOnNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
//                e.onError(new Throwable("happended error!"));
                e.onNext(3);
            }
        })
                //当Observable每发送1次数据事件就会调用1次
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        System.out.println("whh0713---doOnEach..." + integerNotification.getValue());
                    }
                })
                //执行Next事件前调用
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("whh0713---doOnNext..." + integer);
                    }
                })
                //执行Next事件后调用
                .doAfterNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("whh0713---doAfterNext..." + integer);
                    }
                })
                //Observable正常发送事件完毕后调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("whh0713---doOnComplete...");
                    }
                })
                //Observable发送错误事件时调用
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("whh0713---doOnError..." + throwable.getMessage());
                    }
                })
                //发生错误时，发送1个特殊事件 & 正常终止
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        System.out.println("whh0713---onErrorReturn..." + throwable.getMessage());
                        return 0; //TODO　如果发生错误，会将0作为订阅消息发送出去
                    }
                })
                //TODO 遇到错误时，发送1个新的Observable
                //TODO onErrorResumeNext()拦截的错误 = Throwable；若需拦截Exception请用onExceptionResumeNext()
                //TODO 若onErrorResumeNext()拦截的错误 = Exception(e.onError(new Exception("发生错误了"));)，
                //   则会将错误传递给观察者的onError方法
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                        System.out.println("whh0713---onErrorResumeNext..." + throwable.getMessage());
                        //TODO 发生错误事件后，发送一个新的被观察者 & 发送事件序列
                        return Observable.just(11, 22);
                    }
                })
                //观察者订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("whh0713---doOnSubscribe...");
                    }
                })
                //最后执行
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("whh0713---doFinally...");
                    }
                })
                //Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止，在doFinally执行
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("whh0713---doAfterTerminate...");
                    }
                })
                .retry(10) //TODO　重试，即当出现错误时，让被观察者（Observable）重新发射数据，没效果啊！！？？
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("whh0713---subscribe received..." + integer);
                    }
                });
    }

    /**
     * repeat()  无条件地、重复发送 被观察者事件
     */
    private void repeat() {
        Observable.just(1, 2, 3)
                .repeat(3) //TODO　注意：在上面的 doOnNext() 示例中直接使用 repeat() 无效果！
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("whh0713---repeat..." + integer);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }
}