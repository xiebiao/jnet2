package com.github.jnet.v2.impl;

import java.net.InetSocketAddress;

import com.github.jnet.v2.IoAcceptor;

public class LoggingServer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        IoAcceptor acceptor = new LoggingAcceptor(new InetSocketAddress("127.0.0.1", 8080));
        acceptor.bind();
        new Thread(acceptor).start();
    }

}
