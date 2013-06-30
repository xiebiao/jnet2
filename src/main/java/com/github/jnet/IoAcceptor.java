package com.github.jnet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IoAcceptor implements Acceptor {

    protected InetSocketAddress         socketAddress;
    protected Selector                  selector;
    protected ServerSocketChannel       serverSocketChannel;
    protected Processor[]               processors;
    protected int                       nextProcessorIndex = 0;
    protected AbstractConnectionFactory factory;
    private static final Logger         logger             = LoggerFactory.getLogger(IoAcceptor.class);

    public IoAcceptor(InetSocketAddress address, AbstractConnectionFactory factory) throws IOException {
        this.factory = factory;
        this.socketAddress = address;
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();;
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(socketAddress);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        logger.debug("Server started: " + socketAddress);
    }

    @Override
    public final void run() {
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
        } catch (Throwable e) {}
    }

    @Override
    public void setProcessors(Processor[] processors) {
        this.processors = processors;
    }

    protected Processor getNextProcessor() {
        this.nextProcessorIndex = (this.nextProcessorIndex + 1) % this.processors.length;
        return this.processors[this.nextProcessorIndex];
    }

}
