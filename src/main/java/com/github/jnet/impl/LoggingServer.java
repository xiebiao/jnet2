package com.github.jnet.impl;

import java.net.InetSocketAddress;

import com.github.jnet.IoAcceptor;
import com.github.jnet.Processor;

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
        // 设置处理器
        acceptor.setProcessors(processors);
        SimpleConnectionFactory factory = new SimpleConnectionFactory();
        // 设置handler
        acceptor.setHandlerFactory(factory);
        acceptor.bind();
        new Thread(acceptor).start();
    }
}
