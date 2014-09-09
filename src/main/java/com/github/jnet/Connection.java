package com.github.jnet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

public interface Connection {

  public void register(Selector selector) throws IOException;

  public void write(ByteBuffer buffer) throws IOException;

  public void write();

  public void read() throws IOException;

  public boolean close() throws IOException;

}
