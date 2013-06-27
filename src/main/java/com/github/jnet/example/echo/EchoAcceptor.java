package com.github.jnet.example.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.AbstractConnection;
import com.github.jnet.AbstractConnectionFactory;
import com.github.jnet.Acceptor;
import com.github.jnet.Processor;

public class EchoAcceptor implements Acceptor {

    private InetSocketAddress         socketAddress;
    private Selector                  selector;
    private ServerSocketChannel       serverSocketChannel;
    private Processor[]               processors;
    private int                       nextProcessorIndex = 0;
    private AbstractConnectionFactory factory;
    private static final Logger       LOG                = LoggerFactory.getLogger(EchoAcceptor.class);

    public EchoAcceptor(InetSocketAddress address, AbstractConnectionFactory factory) throws IOException {
        this.factory = factory;
        this.socketAddress = address;
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();;
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(socketAddress);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        LOG.debug("Server started: " + socketAddress);
    }

    @Override
    public void run() {
        while (true) {
            Set<SelectionKey> keys = null;
            try {
                selector.select();// block
                keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    if (key.isValid() && key.isAcceptable()) {
                        accept();
                    } else {
                        key.cancel();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                keys.clear();
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
            LOG.debug("接收到新连接");
        } catch (Throwable e) {}
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
