package com.github.jnet.example.echo;

import java.net.InetSocketAddress;

import com.github.jnet.AbstractConnectionFactory;
import com.github.jnet.Acceptor;
import com.github.jnet.Processor;

public class EchoServer {

    /**
     * @param args
     */
    public static void main(String[] args) throws Throwable {
        AbstractConnectionFactory factory = new EchoConnectionFactory();
        Acceptor acceptor = new EchoAcceptor(new InetSocketAddress("127.0.0.1", 8080), factory);
        Processor[] processors = new Processor[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new Processor(i + "");
        }
        // 设置处理器
        acceptor.setProcessors(processors);
        new Thread(acceptor).start();
    }
}
