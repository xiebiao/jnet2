package com.github.jnet.example.echo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.AbstractConnection;
import com.github.jnet.utils.IoUtils;
import com.github.jnet.utils.StringUtils;

public class EchoConnection extends AbstractConnection {

    private static final byte[] SERVER_SAY   = "Server say:".getBytes();
    private static int          READ_HEADER  = 0;
    private static int          READ_BODY    = 1;
    private int                 currentState = READ_HEADER;
    private static final Logger LOG          = LoggerFactory.getLogger(EchoConnection.class);

    public EchoConnection(SocketChannel channel) {
        super(channel);

    }

    public void read() throws IOException {
        readBuffer.limit(readBuffer.position() + BUF_SIZE);
        IoUtils.read(this.channel, this.readBuffer);
        int len = readBuffer.position();
        byte b = readBuffer.getByte(len - 1);
        if (b == (byte) '\n') {
            LOG.debug(StringUtils.dumpAsHex(readBuffer.readBytes(0, len), len));
            this.writeBuffer.position(0);
            this.writeBuffer.writeBytes("Server say:".getBytes());
            this.writeBuffer.writeBytes(readBuffer.readBytes(0, len));
            this.writeBuffer.position(0);
            this.writeBuffer.limit(SERVER_SAY.length + len);

            this.readBuffer.position(0);
            this.readBuffer.limit(0);
            this.processor.write(this);
        }

    }

    @Override
    public void write(ByteBuffer buffer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void write() {
        try {
            if ((processKey.interestOps() & SelectionKey.OP_WRITE) == 0) {
                int len = this.writeBuffer.position();
                byte[] bs = new byte[len];
                this.writeBuffer.getBuffer().get(bs);
                LOG.debug(StringUtils.dumpAsHex(bs, len));
                this.channel.write(this.writeBuffer.getBuffer());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean close() {
        try {
            this.channel.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
