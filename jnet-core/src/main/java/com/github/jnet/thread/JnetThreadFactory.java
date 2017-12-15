package com.github.jnet.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiebiao
 * @date 2017/12/15.
 */
public class JnetThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber;
    private final String name;
    private final boolean isDaemon;
    private final ThreadGroup group;

    public JnetThreadFactory(String name, boolean isDaemon) {
        this.threadNumber = new AtomicInteger(0);
        this.name = name;
        this.isDaemon = isDaemon;
        SecurityManager sm = System.getSecurityManager();
        this.group = (sm != null) ? sm.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, name + "-" + threadNumber.getAndIncrement());
        t.setDaemon(isDaemon);
        return t;
    }
}
