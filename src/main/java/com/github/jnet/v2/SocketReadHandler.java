package com.github.jnet.v2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class SocketReadHandler implements Runnable {

    final SocketChannel socket;
    final SelectionKey  skey;
    final Selector selector;
    static final int    READING = 0, SENDING = 1;
    int                 state   = READING;

    public SocketReadHandler(SocketChannel socket, Selector selector) throws IOException {
        this.socket = socket;
        this.selector = selector;
        this.socket.configureBlocking(false);
        this.skey = this.socket.register(selector, SelectionKey.OP_READ);
        this.skey.attach(this);
        this.skey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            //System.out.println("SocketReadHandler readRequest");
            readRequest();
            //selector.selectedKeys().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void readRequest() throws Exception {
        ByteBuffer input = ByteBuffer.allocate(1024);
        input.clear();
        int bytesRead = socket.read(input);
        // 激活线程池 处理这些request
        System.out.println(new String(input.array()));
        // requestHandle(new Request(socket,btt));
    }
}
