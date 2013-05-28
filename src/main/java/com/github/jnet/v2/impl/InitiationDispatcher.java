package com.github.jnet.v2.impl;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.jnet.JnetThreadFactory;
import com.github.jnet.v2.Couple;
import com.github.jnet.v2.Dispatcher;
import com.github.jnet.v2.EventHandler;
import com.github.jnet.v2.EventType;

/**
 * 单实例
 * @author xiebiao[谢彪]
 * @email xiebiao@jd.com
 */
public class InitiationDispatcher implements Dispatcher<EventHandler> {

    private static InitiationDispatcher                            dispatcher;
    private ConcurrentLinkedQueue<Couple<EventType, EventHandler>> handlerQueue = new ConcurrentLinkedQueue<Couple<EventType, EventHandler>>();
    private ExecutorService                                        executor;
    private Selector                                               selector;

    private InitiationDispatcher() {
        executor = Executors.newFixedThreadPool(10, new JnetThreadFactory());
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public final static Dispatcher<EventHandler> getInstance() {
        if (dispatcher == null) {
            dispatcher = new InitiationDispatcher();
        }
        return dispatcher;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.selector.select();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            handleEvents();
        }

    }

    @Override
    public void handleEvents() {
        Couple<EventType, EventHandler> couple = handlerQueue.poll();
        if (couple != null) {

        }
    }

    @Override
    public void remove(EventHandler handler, EventType eventType) {
        handlerQueue.remove(new Couple<EventType, EventHandler>(eventType, handler));
    }

    @Override
    public void register(EventHandler handler, EventType eventType) {
        handlerQueue.add(new Couple<EventType, EventHandler>(eventType, handler));
        this.selector.wakeup();
    }

}
