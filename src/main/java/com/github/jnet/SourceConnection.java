package com.github.jnet;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public abstract class SourceConnection extends AbstractConnection {

    public SourceConnection(SocketChannel channel) throws IOException {
        super(channel);
    }

}
