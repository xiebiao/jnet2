package com.github.jnet.nio;

public class AbstractHandler implements IoHandler {

  protected AbstractConnection connection;

  public AbstractHandler(AbstractConnection connection) {
    this.connection = connection;

  }

  @Override
  public void handle(byte[] data) {

  }

}
