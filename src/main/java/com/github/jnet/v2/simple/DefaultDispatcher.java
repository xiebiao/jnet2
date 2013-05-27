package com.github.jnet.v2.simple;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.github.jnet.v2.Dispatcher;
import com.github.jnet.v2.Session;

public class DefaultDispatcher implements Dispatcher {

    private Queue<Session>  sessionQueue = new ConcurrentLinkedQueue<Session>();
    private volatile Object lock         = new Object();

    public DefaultDispatcher() {
        System.out.println("new DefaultDispatcher");
    }

    @Override
    public void dispatch() {
        synchronized (lock) {
            Session session = sessionQueue.poll();
            if (session != null) {
                System.out.println("dispatch session");

            }
        }
    }

    @Override
    public void run() {
        while (true) {
            this.dispatch();
        }
    }

    @Override
    public void registerSession(Session session) {
        synchronized (lock) {
            if (this.sessionQueue == null) {
                System.out.println("sessionQueue is null");
            }
            this.sessionQueue.add(session);
        }

    }

    @Override
    public void removeSession(Session session) {
        synchronized (lock) {
            this.sessionQueue.remove(session);
        }

    }

}
