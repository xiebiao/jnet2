package com.github.jnet.v3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {

    private final Selector            selector;
    private final ServerSocketChannel serverChannel;

    public Acceptor(InetSocketAddress socketAddress) throws IOException {
        this.selector = Selector.open();
        this.serverChannel = ServerSocketChannel.open();
        this.serverChannel.socket().bind(socketAddress);
        this.serverChannel.configureBlocking(false);
        this.serverChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    @Override
    public void run() {
        while (true) {

        }

    }

    private void accept() {
        SocketChannel channel = null;

    }
}
