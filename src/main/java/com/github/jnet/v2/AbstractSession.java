package com.github.jnet.v2;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.github.jnet.buffer.IoBuffer;

public abstract class AbstractSession implements Session {

    protected IoBuffer      writeBuffer;
    protected IoBuffer      readBuffer;
    protected SocketChannel socket;
    protected Selector      selector;

    public AbstractSession(SocketChannel socketChannel, Selector selector) {
        this.socket = socketChannel;
        this.selector = selector;
        this.readBuffer = new IoBuffer();
        this.writeBuffer = new IoBuffer();
        System.out.println("new AbstractSession");
    }
}
