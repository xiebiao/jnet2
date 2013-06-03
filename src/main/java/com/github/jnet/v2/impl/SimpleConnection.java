package com.github.jnet.v2.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.github.jnet.v2.AbstractConnection;

public class SimpleConnection extends AbstractConnection {

    private static int READ_HEADER  = 0;
    private static int READ_BODY    = 1;
    private int        currentState = READ_HEADER;

    public SimpleConnection(SocketChannel channel) {
        super(channel);

    }

    public void read() {
        ByteBuffer b = ByteBuffer.allocate(1024);
        try {
            this.channel.read(b);
            System.out.println(new String(b.array()));
            this.writeBuffer.writeBytes(b.array());
            this.processor.write(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuffer buffer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void write() {
        try {
            System.out.println("SimpleConnection is writing");
            if ((processKey.interestOps() & SelectionKey.OP_WRITE) == 0) {
                this.channel.write(this.writeBuffer.getBuffer());
            }
            processKey.selector().wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean close() {
        return false;
    }
}
