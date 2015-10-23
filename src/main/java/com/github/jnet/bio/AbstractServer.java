package com.github.jnet.bio;

import java.net.InetSocketAddress;

/**
 * @author xiebiao
 */
public abstract class AbstractServer extends Server {

    public AbstractServer(InetSocketAddress socketAddress) {
        super(socketAddress);
        this.maxConnection = 10;
    }

    @Override
    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

}
