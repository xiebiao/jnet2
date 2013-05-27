package com.github.jnet.v2;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {

    private ServerSocketChannel serverSocketChannel = null;

    private Selector            selector            = null;

    public Acceptor(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println("接收到来自客户端（" + socketChannel.socket().getInetAddress().getHostAddress() + "）的连接");
                new Handler(socketChannel, selector);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
