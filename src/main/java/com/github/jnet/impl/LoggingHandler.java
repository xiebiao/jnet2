package com.github.jnet.impl;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.github.jnet.EventHandler;
import com.github.jnet.EventType;

public class LoggingHandler implements EventHandler {

    private Selector      selector;
    private SocketChannel socket;
    private SelectionKey  sk;

    public LoggingHandler(Selector selector, SocketChannel socket) {
        try {
            this.selector = selector;
            this.socket = socket;
            sk = socket.register(selector, 0);
            socket.configureBlocking(false);
            sk.attach(this);
            sk.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void handleEvent(EventType event) {

    }

    @Override
    public EventHandler getHandler() {
        // TODO Auto-generated method stub
        return null;
    }

}
