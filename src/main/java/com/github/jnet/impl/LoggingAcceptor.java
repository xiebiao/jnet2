package com.github.jnet.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import com.github.jnet.AbstractConnection;
import com.github.jnet.AbstractConnectionFactory;
import com.github.jnet.IoAcceptor;
import com.github.jnet.Processor;

public class LoggingAcceptor extends IoAcceptor {

    private InetSocketAddress         socketAddress;
    private Selector                  selector;
    private ServerSocketChannel       serverSocketChannel;
    private Processor[]               processors;
    private int                       nextProcessorIndex = 0;
    private AbstractConnectionFactory factory;

    public LoggingAcceptor(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        this.factory = new SimpleConnectionFactory();
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();// block
                Set<SelectionKey> keys = selector.selectedKeys();
                try {
                    for (SelectionKey key : keys) {
                        if (key.isValid() && key.isAcceptable()) {
                            accept();
                        } else {
                            key.cancel();
                        }
                    }
                } finally {
                    keys.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void accept() {
        SocketChannel channel = null;
        try {
            channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            AbstractConnection connection = factory.create(channel);
            Processor processor = this.getNextProcessor();
            connection.setProcessor(processor);
            processor.register(connection);
        } catch (Throwable e) {}
    }

    @Override
    public void bind() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();;
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(socketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server started");
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setProcessors(Processor[] processors) {
        this.processors = processors;
    }

    private Processor getNextProcessor() {
        this.nextProcessorIndex = (this.nextProcessorIndex + 1) % this.processors.length;
        return this.processors[this.nextProcessorIndex];
    }

}
