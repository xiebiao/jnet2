package com.github.jnet.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.AbstractConnection;
import com.github.jnet.utils.IoUtils;

public class SimpleConnection extends AbstractConnection {

    private static int          READ_HEADER  = 0;
    private static int          READ_BODY    = 1;
    private int                 currentState = READ_HEADER;
    private static final Logger LOG          = LoggerFactory.getLogger(SimpleConnection.class);

    public SimpleConnection(SocketChannel channel) {
        super(channel);

    }

    public void read() throws IOException {
        readBuffer.limit(readBuffer.position() + BUF_SIZE);
        IoUtils.read(this.channel, this.readBuffer);
        int len = readBuffer.position();
        LOG.debug("客户端数据:" + new String(readBuffer.readBytes(0, len)));
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
