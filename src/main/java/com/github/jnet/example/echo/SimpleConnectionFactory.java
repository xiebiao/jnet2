package com.github.jnet.example.echo;

import java.nio.channels.SocketChannel;

import com.github.jnet.AbstractConnectionFactory;

public class SimpleConnectionFactory extends AbstractConnectionFactory {

    @Override
    protected SimpleConnection getConnection(SocketChannel channel) {
        SimpleConnection con = new SimpleConnection(channel);
        return con;
    }

}
