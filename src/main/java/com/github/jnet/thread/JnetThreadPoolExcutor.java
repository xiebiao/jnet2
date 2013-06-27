package com.github.jnet.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JnetThreadPoolExcutor extends ThreadPoolExecutor {

    public JnetThreadPoolExcutor(int poolSize,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(poolSize, poolSize, Long.MAX_VALUE, TimeUnit.NANOSECONDS, workQueue, threadFactory);
    }
}
