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

    private static final byte[] SERVER_SAY = "Server say:".getBytes();
    private static final Logger log        = LoggerFactory.getLogger(EchoConnection.class);

    public EchoConnection(SocketChannel channel) {
        super(channel);

    }

    public void read() throws IOException {
        this.readBuffer.limit(readBuffer.position() + bufferMaxSize);
        IoUtils.read(this.channel, this.readBuffer);
        int len = readBuffer.position();
        byte lastByte = readBuffer.getByte(len - 1);
        byte[] exit = readBuffer.getBytes(0, len - 1);

        if (lastByte == (byte) '\n') {
    
            if (new String(exit).trim().equalsIgnoreCase("exit")) {
                this.close();
                return;
            }
            log.debug(StringUtils.dumpAsHex(readBuffer.readBytes(0, len), len));
            this.writeBuffer.position(0);
            this.writeBuffer.writeBytes(SERVER_SAY);
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
            this.readBuffer.clear();
            this.writeBuffer.clear();
            return this.isClosed.getAndSet(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
