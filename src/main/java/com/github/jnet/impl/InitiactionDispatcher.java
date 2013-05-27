package com.github.jnet.impl;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.github.jnet.Dispatcher;
import com.github.jnet.EventHandler;
import com.github.jnet.EventType;

/**
 * 单例
 * @author xiebiao[谢彪]
 * @email xiebiao@jd.com
 */
public class InitiactionDispatcher implements Dispatcher, Runnable {

    private Selector            selector;
    private SocketChannel       socket;
    private volatile Object     lock     = new Object();
    private Queue<EventHandler> handlers = new ConcurrentLinkedQueue<EventHandler>();

    public InitiactionDispatcher(SocketChannel serverSocket, Selector selector) {
        this.selector = selector;
        this.socket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            this.handleEvent();
        }

    }

    @Override
    public void handleEvent() {

        EventHandler handler = this.handlers.poll();
        if (handler != null) {
            // handler.handleEvent(event);
        }

    }

    @Override
    public void registerHandler(EventHandler handler, EventType event) {
        synchronized (lock) {
            this.handlers.add(handler);
        }

    }

    @Override
    public void removeHandler(EventHandler handler, EventType event) {
        synchronized (lock) {
            this.handlers.remove(handler);
        }
    }

}
