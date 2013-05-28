package com.github.jnet.v2;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Worker implements Runnable {

    private SocketChannel socketChannel;
    private Selector      selector;

    public Worker(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {

        }

    }

}
