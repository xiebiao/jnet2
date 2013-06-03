package com.github.jnet.v2;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.github.jnet.buffer.IoBuffer;

public abstract class AbstractConnection implements Connection {

    protected final SocketChannel channel;
    protected SelectionKey        processKey;
    protected Processor           processor;
    protected IoBuffer            readBuffer  = null;
    protected IoBuffer            writeBuffer = null;

    public AbstractConnection(SocketChannel channel) {
        this.channel = channel;
        readBuffer = new IoBuffer();
        writeBuffer = new IoBuffer();
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void register(Selector selector) throws IOException {
        processKey = channel.register(selector, SelectionKey.OP_READ, this);
    }

}
