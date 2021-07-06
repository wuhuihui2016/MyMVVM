package com.whh.thread;

/**
 * 快递实体类
 *
 * author:wuhuihui 2021.07.01
 */
public class Express {
    public final static String CITY = "ShangHai";
    private int km; //运输距离
    private String site; //到达地点

    public Express() {
    }

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /**
     * 更新距离，并通知相关的所有线程
     */
    public synchronized void changeKm(){
        this.km = 101;
        notifyAll();
    }

    /**
     * 更新地点，并通知相关的所有线程
     */
    public synchronized void changeSite(){
        this.site = "BeiJing";
        notifyAll();
    }

    /**
     * 线程等待距离的变化
     */
    public synchronized void waitKm(){
        while (this.km < 100) {
            try {
                wait();
                System.out.println("check Site thread["
                        + Thread.currentThread().getName()
                 + "] is be notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the Km is " + this.km + ", I will chage Km");
    }

    /**
     * 线程等待地点的变化
     */
    public synchronized void waitSite() {
        while (this.site.equals(CITY)) { //快递到达终点
            try {
                wait();
                System.out.println("check Site thread["
                        + Thread.currentThread().getName()
                        + "] is be notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the site is " + this.site + ", I will chage site");
    }
}
