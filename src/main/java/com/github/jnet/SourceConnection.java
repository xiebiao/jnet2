package com.github.jnet;

import java.nio.channels.SocketChannel;


public abstract class SourceConnection extends AbstractConnection {

    public SourceConnection(SocketChannel channel) {
        super(channel);
    }
   
}
