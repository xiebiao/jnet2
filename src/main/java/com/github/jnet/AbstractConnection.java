package com.github.jnet;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.buffer.IoBuffer;

public abstract class AbstractConnection implements Connection {

    protected final SocketChannel channel;
    protected SelectionKey        processKey;
    protected Processor           processor;
    protected IoBuffer            readBuffer   = null;
    protected IoBuffer            writeBuffer  = null;
    protected AtomicBoolean       isRegistered = new AtomicBoolean(false);
    protected static final int    BUF_SIZE     = 1024;
    protected Handler             handler;
    private static final Logger   logger          = LoggerFactory.getLogger(AbstractConnection.class);

    public AbstractConnection(SocketChannel channel) {
        this.channel = channel;
        readBuffer = new IoBuffer();
        readBuffer.position(0);
        readBuffer.limit(0);

        writeBuffer = new IoBuffer();
        writeBuffer.position(0);
        writeBuffer.limit(0);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void register(Selector selector) throws IOException {
        if (isRegistered.get() == false) {
            processKey = channel.register(selector, SelectionKey.OP_READ, this);
            isRegistered.set(true);
        } 
    }

}
