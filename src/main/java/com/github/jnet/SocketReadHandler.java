package com.github.jnet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.github.jnet.impl.InitiactionDispatcher;

public class SocketReadHandler implements EventHandler, Runnable {

    private SocketChannel socket;
    private Selector      selector;
    private Dispatcher    dispatcher;

    public SocketReadHandler(SocketChannel socket, Selector selector) {
        this.socket = socket;
        this.selector = selector;
        this.dispatcher = new InitiactionDispatcher(socket, selector);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleEvent(EventType event) {
        ByteBuffer input = ByteBuffer.allocate(1024);
        input.clear();
        try {
            int bytes = socket.read(input);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public EventHandler getHandler() {
        // TODO Auto-generated method stub
        return null;
    }

}
