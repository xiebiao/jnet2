package com.github.jnet.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public abstract class TargetConnection extends AbstractConnection {

    protected String      host;
    protected int         port;
    protected IoConnector connector;

    public TargetConnection(SocketChannel channel) throws IOException {
        super(channel);
    }

    public void connect(Selector selector) throws IOException {
        channel.connect(new InetSocketAddress(host, port));
        processKey = channel.register(selector, SelectionKey.OP_CONNECT, this);
        System.out.println("connect...");

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
