package com.github.jnet.v2.simple;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.github.jnet.v2.AbstractSession;
import com.github.jnet.v2.Session;

public class DefaultSession extends AbstractSession implements Session {

    private static int READ_HEADER  = 0;
    private static int READ_BODY    = 1;
    private int        currentState = READ_HEADER;

    public DefaultSession(SocketChannel socketChannel, Selector selector) {
        super(socketChannel, selector);
        System.out.println("new DefaultSession");
    }

    @Override
    public void open() {
        // TODO Auto-generated method stub

    }

    @Override
    public void readCompleted() {
        if (currentState == READ_HEADER) {
            System.out.println("READ_HEADER");
            currentState = READ_BODY;
        } else {
            System.out.println("READ_BODY");
            currentState = READ_HEADER;

        }

    }

    @Override
    public void reading() {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeCompleted() {
        // TODO Auto-generated method stub

    }

    @Override
    public void writing() {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

}
