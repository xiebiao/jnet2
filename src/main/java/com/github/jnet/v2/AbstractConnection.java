package com.github.jnet.v2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.github.jnet.buffer.IoBuffer;

public abstract class AbstractConnection implements Connection {

    protected final SocketChannel channel;
    protected SelectionKey        processKey;
    protected Processor           processor;
    protected IoBuffer      readBuffer   = null;
    protected IoBuffer      writeBuffer  = null;
    
    public AbstractConnection(SocketChannel channel) {
        this.channel = channel;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void register(Selector selector) throws IOException {
        channel.register(selector, SelectionKey.OP_READ, this);

    }

    @Override
    public void write(ByteBuffer buffer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void write() {
        // TODO Auto-generated method stub

    }

    @Override
    public void read() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean close() {
        // TODO Auto-generated method stub
        return false;
    }

}
