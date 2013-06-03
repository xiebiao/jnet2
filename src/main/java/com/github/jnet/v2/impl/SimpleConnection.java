package com.github.jnet.v2.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
