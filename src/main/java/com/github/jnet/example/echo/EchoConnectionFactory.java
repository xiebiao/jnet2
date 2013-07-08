package com.github.jnet.example.echo;

import java.nio.channels.SocketChannel;

import com.github.jnet.factory.AbstractConnectionFactory;

public class EchoConnectionFactory extends AbstractConnectionFactory {

    @Override
    protected EchoConnection getConnection(SocketChannel channel) {
        EchoConnection con = new EchoConnection(channel);
        return con;
    }

}
