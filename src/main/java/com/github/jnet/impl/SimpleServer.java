package com.github.jnet.impl;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.jnet.AbstractConnectionFactory;
import com.github.jnet.Acceptor;
import com.github.jnet.JnetThreadFactory;
import com.github.jnet.Processor;

public class SimpleServer {

    /**
     * @param args
     */
    public static void main(String[] args) throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2, new JnetThreadFactory());
        AbstractConnectionFactory factory = new SimpleConnectionFactory();
        Acceptor acceptor = new SimpleAcceptor(new InetSocketAddress("10.28.164.70", 8080), factory);
        Processor[] processors = new Processor[2];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new Processor();
        }
        // 设置处理器
        acceptor.setProcessors(processors);
        executor.execute(acceptor);
    }
}
