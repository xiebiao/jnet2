package com.github.jnet.v2;

import java.nio.channels.SocketChannel;

public abstract class AbstractConnectionFactory {

    protected abstract AbstractConnection getConnection(SocketChannel channel);

    public AbstractConnection create(SocketChannel channel) {
        return this.getConnection(channel);
    }
}
