package com.github.jnet.nio;

import java.io.IOException;

public final class IoProcessor {

  private IoReactor reactor;

  public IoProcessor(String name) {
    try {
      reactor = new IoReactor(name);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void register(Connection connection) {
    reactor.read(connection);
  }

  public void write(Connection connection) {
    reactor.write(connection);
  }
}
