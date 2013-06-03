package com.github.jnet.v2.impl;

import java.nio.channels.SocketChannel;

import com.github.jnet.v2.AbstractConnectionFactory;

public class SimpleConnectionFactory extends AbstractConnectionFactory {

    @Override
    protected SimpleConnection getConnection(SocketChannel channel) {
        SimpleConnection con = new SimpleConnection(channel);
        return con;
    }

}
