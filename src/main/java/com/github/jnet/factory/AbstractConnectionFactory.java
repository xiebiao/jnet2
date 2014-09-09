package com.github.jnet.factory;

import java.nio.channels.SocketChannel;

import com.github.jnet.AbstractConnection;

public abstract class AbstractConnectionFactory {

  protected abstract AbstractConnection getConnection(SocketChannel channel);

  public AbstractConnection create(SocketChannel channel) {
    return this.getConnection(channel);
  }
}
