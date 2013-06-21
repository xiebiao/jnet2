package com.github.jnet.impl;

import java.net.InetSocketAddress;

import com.github.jnet.AbstractConnectionFactory;
import com.github.jnet.Acceptor;
import com.github.jnet.Processor;

public class SimpleServer {

    /**
     * @param args
     */
    public static void main(String[] args) throws Throwable {
        AbstractConnectionFactory factory = new SimpleConnectionFactory();
        Acceptor acceptor = new SimpleAcceptor(new InetSocketAddress("127.0.0.1", 8080), factory);
        Processor[] processors = new Processor[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new Processor(i + "");
        }
        // 设置处理器
        acceptor.setProcessors(processors);
        new Thread(acceptor).start();
    }
}
