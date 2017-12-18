package com.github.jnet.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JnetThreadPoolExcutor extends ThreadPoolExecutor {

    private String name;

    public JnetThreadPoolExcutor(
            String name, int poolSize,
            BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory) {
        super(poolSize,
                poolSize,
                Long.MAX_VALUE,
                TimeUnit.NANOSECONDS,
                workQueue,
                threadFactory);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
