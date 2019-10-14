package com.example.base;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GeelyThreadPool {
    private final int CORE_THREAD_NUMBERS = 30;
    private final int MAX_THREAD_NUMBERS = 30;
    private final long KEEP_ALIVE_TIME = 0L;
    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private ExecutorService threadPoolExecutor = new ThreadPoolExecutor(CORE_THREAD_NUMBERS, MAX_THREAD_NUMBERS, KEEP_ALIVE_TIME, TimeUnit.MINUTES, workQueue);
    private static volatile GeelyThreadPool mInstance;

    private GeelyThreadPool() {
    }

    public static GeelyThreadPool getInstance() {
        if (mInstance == null) {
            synchronized (GeelyThreadPool.class) {
                if (mInstance == null) {
                    mInstance = new GeelyThreadPool();
                }
            }
        }
        return mInstance;
    }

    public Future<?> submit(Runnable runnable) {
        if (runnable == null) {
            return null;
        }
        return threadPoolExecutor.submit(runnable);
    }

    public void execute(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        threadPoolExecutor.execute(runnable);
    }
}
