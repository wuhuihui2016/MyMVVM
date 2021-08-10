package com.whh.others.genericity;

import java.util.concurrent.atomic.AtomicInteger;

public class Base<T> {
    AtomicInteger updataCount = new AtomicInteger();
    private T value;

    public void setValue(T value) {
        System.out.println("called Base.setValue");
        this.value = value;
        updataCount.incrementAndGet();
    }

    @Override
    public String toString() {
        return String.format("value：%s updataCount：%d", value, updataCount.get());
    }
}
