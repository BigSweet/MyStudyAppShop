package com.demo.swt.mystudyappshop.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/12/26
 */
public class ThreadPoolUntils {
    static ExecutorService fixedThreadPool;
    static ExecutorService SingleThreadExecutor;
    static ExecutorService newCachedThreadPool;
    static ExecutorService newScheduledThreadPool;

    //创建一个固定线程数量的线程池，都是核心线程
    public static ExecutorService getFixThreadPool() {
        if (fixedThreadPool == null) {
            fixedThreadPool = Executors.newFixedThreadPool(3);
        }
        return fixedThreadPool;
    }


    //创建一个只有一个线程的线程池，每次只能执行一个线程任务，多余的任务会保存到一个任务队列中，等待线程处理完再依次处理任务队列中的任务
    public static ExecutorService getSingleThreadPool() {
        if (SingleThreadExecutor == null) {
            SingleThreadExecutor = Executors.newSingleThreadExecutor();
        }
        return SingleThreadExecutor;
    }

    //创建一个可以根据实际情况调整线程池中线程的数量的线程池
    public static ExecutorService getnewCachedThreadPool() {
        if (newCachedThreadPool == null) {
            newCachedThreadPool = Executors.newCachedThreadPool();
        }
        return newCachedThreadPool;
    }

    //创建一个可以定时或者周期性执行任务的线程池
    public static ExecutorService getnewScheduledThreadPool() {
        if (newScheduledThreadPool == null) {
            newScheduledThreadPool = Executors.newScheduledThreadPool(3);
        }
        return newScheduledThreadPool;
    }

    //自定义线程类为PausableThreadPoolExecutor

}
