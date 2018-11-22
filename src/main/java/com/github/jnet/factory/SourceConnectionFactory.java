package com.github.jnet.factory;

import java.nio.channels.SocketChannel;

import com.github.jnet.nio.SourceConnection;

public abstract class SourceConnectionFactory extends AbstractConnectionFactory {

    @Override
    protected SourceConnection getConnection(SocketChannel channel) {

        return null;
    }

}
