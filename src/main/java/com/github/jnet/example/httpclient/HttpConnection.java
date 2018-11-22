package com.github.jnet.example.httpclient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.github.jnet.nio.TargetConnection;

public class HttpConnection extends TargetConnection {

    public HttpConnection(SocketChannel channel) throws IOException {
        super(channel);
        channel.configureBlocking(false);
    }

    @Override
    public void write(ByteBuffer buffer) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void write() {
        String get = "GET /index.html http/1.1";
        try {
            this.writeBuffer.writeBytes("http".getBytes());
            this.channel.write(this.writeBuffer.getBuffer());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void read() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean close() throws IOException {
        System.out.println("close");
        return false;
    }

}
