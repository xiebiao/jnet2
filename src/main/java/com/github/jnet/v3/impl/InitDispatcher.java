package com.github.jnet.v3.impl;

import java.io.IOException;
import java.nio.channels.Selector;

import com.github.jnet.v3.Acceptor;
import com.github.jnet.v3.Dispatcher;

public final class InitDispatcher implements Dispatcher {

    private static InitDispatcher dispatcher;
    private Acceptor[]            acceptors;
    private Selector              selector;

    private InitDispatcher() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InitDispatcher getDispatcher() {
        synchronized (InitDispatcher.class) {
            if (dispatcher == null) {
                dispatcher = new InitDispatcher();
            }
            return dispatcher;
        }

    }

    @Override
    public void run() {
        while (true) {
            handleEvents();
        }

    }

    @Override
    public void handleEvents() {
        for(Acceptor acceptor:acceptors){
        
        }
    }

    @Override
    public void register(Acceptor[] acceptors) {
        this.acceptors = acceptors;

    }

}
