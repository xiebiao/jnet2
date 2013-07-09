package com.github.jnet.example.echo;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.github.jnet.IoAcceptor;
import com.github.jnet.IoProcessor;
import com.github.jnet.factory.AbstractConnectionFactory;

public class EchoServer extends IoAcceptor{

    public EchoServer(InetSocketAddress address, AbstractConnectionFactory factory) throws IOException {
        super(address, factory);
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Throwable {
        AbstractConnectionFactory factory = new EchoConnectionFactory();
        EchoServer acceptor = new EchoServer(new InetSocketAddress("127.0.0.1", 8080), factory);
        IoProcessor[] processors = new IoProcessor[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < processors.length; i++) {
            processors[i] = new IoProcessor(i + "");
        }
        // 设置处理器
        acceptor.setProcessors(processors);
        new Thread(acceptor).start();
    }
}
