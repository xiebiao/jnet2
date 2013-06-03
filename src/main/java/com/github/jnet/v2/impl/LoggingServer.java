package com.github.jnet.v2.impl;

import java.net.InetSocketAddress;

import com.github.jnet.v2.IoAcceptor;
import com.github.jnet.v2.Processor;

public class LoggingServer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        IoAcceptor acceptor = new LoggingAcceptor(new InetSocketAddress("127.0.0.1", 8080));
        Processor[] processors = new Processor[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new Processor();
        }
        acceptor.setProcessors(processors);
        acceptor.bind();
        new Thread(acceptor).start();
    }
}
