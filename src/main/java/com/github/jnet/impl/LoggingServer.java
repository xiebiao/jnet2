package com.github.jnet.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Map;

import com.github.jnet.Server;

public class LoggingServer implements Server {

    private Selector            selector;
    private ServerSocketChannel serverSocket;

    @Override
    public void setOptions(Map<String, Object> options) {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(InetSocketAddress socketAddress) {
        try {
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();;
            serverSocket.configureBlocking(false);
            serverSocket.socket().bind(socketAddress);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {

    }

    public static final void main(String args[]) {
        LoggingServer server = new LoggingServer();
        server.init(new InetSocketAddress("127.0.0.1", 8081));
        server.start();
    }

}
