package com.github.jnet.example.httpclient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.github.jnet.TargetConnection;


public class HttpConnection extends TargetConnection {

    public HttpConnection(SocketChannel channel)  throws IOException{
        super(channel);
        channel.configureBlocking(false);
    }

    @Override
    public void write(ByteBuffer buffer) throws IOException {
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
    public boolean close() throws IOException {
        System.out.println("close");
        return false;
    }

}
