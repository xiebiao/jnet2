package com.github.jnet.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.github.jnet.AbstractConnection;
import com.github.jnet.utils.IoUtils;
import com.github.jnet.utils.StringUtils;

public class SimpleConnection extends AbstractConnection {

    private static int       READ_HEADER  = 0;
    private static int       READ_BODY    = 1;
    private int              currentState = READ_HEADER;
    private static final int BUF_SIZE     = 512;

    public SimpleConnection(SocketChannel channel) {
        super(channel);

    }

    public void read() {
        try {
            IoUtils.read(channel, this.readBuffer);
            System.out.println(new String(this.readBuffer.getBytes(this.readBuffer.capacity())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println("readBuffer.position():"+readBuffer.position());
        // byte b = readBuffer.getByte(readBuffer.position() - 1);
        // if (b == (byte) '\n') {
        // System.out.println("read \n");
        // int len = readBuffer.position();
        // this.writeBuffer.position(0);
        // writeBuffer.writeBytes("Server say:".getBytes());
        // writeBuffer.writeBytes(readBuffer.readBytes(0, len));
        // writeBuffer.position(0);
        // writeBuffer.limit(len);
        // this.write();
        // return;
        // }
        // int remain = readBuffer.remaining();
        // readBuffer.limit(readBuffer.position() + remain);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean close() {
        return false;
    }
}
