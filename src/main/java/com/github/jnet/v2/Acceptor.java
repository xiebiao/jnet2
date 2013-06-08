package com.github.jnet.v2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Acceptor implements Runnable {

    private Selector            selector;
    private ServerSocketChannel serverSocket;

    private SelectionKey        skey;
    private ExecutorService     executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Acceptor(InetSocketAddress address) throws IOException {

        serverSocket = ServerSocketChannel.open();
        serverSocket.configureBlocking(false);
        serverSocket.socket().bind(address);

    }

    public void register(Selector selector) throws IOException {
        this.selector = selector;
        skey = serverSocket.register(selector, SelectionKey.OP_ACCEPT, this);
    }

    public void accept() {

    }

    @Override
    public void run() {
        try {
            SocketChannel socket = serverSocket.accept();
            if (socket != null) {
                System.out.println("Acceptor run:"+this.skey.interestOps());
                executor.execute(new SocketReadHandler(socket, selector));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
