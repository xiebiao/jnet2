package com.github.jnet.v2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class Reactor implements Runnable {

    private ServerSocketChannel serverSocketChannel = null;
    private Selector            selector            = null;

    public Reactor() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8888));
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.attach(new Acceptor(serverSocketChannel, selector));
            System.out.println("服务器启动正常!");
        } catch (IOException e) {
            System.out.println("启动服务器时出现异常!");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey selectionKey = iter.next();
                    dispatch((Runnable) selectionKey.attachment());
                    iter.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void dispatch(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }
}
