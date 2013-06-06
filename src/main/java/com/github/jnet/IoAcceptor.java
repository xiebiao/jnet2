package com.github.jnet;

import java.net.InetSocketAddress;

public abstract class IoAcceptor implements Acceptor {

    public abstract void bind(InetSocketAddress address);

}
