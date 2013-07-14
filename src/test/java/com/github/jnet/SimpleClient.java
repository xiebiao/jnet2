package com.github.jnet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * http://shift-alt-ctrl.iteye.com/blog/1840407
 * @author xiebiao
 */
public class SimpleClient {

    // private InetSocketAddress address;
    private Selector      selector;
    private SocketChannel channel;

    public SimpleClient(InetSocketAddress address) throws IOException {
        // this.address = address;
        this.selector = Selector.open();
        channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(address);
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void write() throws IOException {
        selector.select();
        Iterator<SelectionKey> keys = selector.keys().iterator();
        while (keys.hasNext()) {
            SelectionKey key = keys.next();
            // 连接事件
            if (key.isConnectable()) {
                System.out.println("a");
                // SocketChannel socketChannel = (SocketChannel) key.channel();
                // if (channel.isConnectionPending()) channel.finishConnect();
                if (channel.finishConnect()) {
                    channel.write(ByteBuffer.wrap("ok".getBytes()));// 向服务器发信息,信息中即服务器上的文件名
                    channel.register(selector, SelectionKey.OP_READ);
                }

            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        SimpleClient sc = new SimpleClient(new InetSocketAddress("127.0.0.1", 8080));
        sc.write();
    }
}
