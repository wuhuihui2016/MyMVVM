package com.whh.others.jvm.ex2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author King老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：hashmap死循环问题
 */
public class HashMapConcurrentProblem extends Thread{
    private static final Map<Integer, Integer> map = new ConcurrentHashMap<>();
    private static final AtomicInteger at = new AtomicInteger(0);


    @Override
    public void run() {
        while(at.get()<2000000){
            map.put(at.get(), at.get());
            at.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            Thread thread = new HashMapConcurrentProblem();
            thread.start();
        }
    }
}
