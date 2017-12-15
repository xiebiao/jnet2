package com.github.jnet.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

public interface Connection {

  void register(Selector selector) throws IOException;

  void write(ByteBuffer buffer) throws IOException;

  void write();

  void read() throws IOException;

  boolean close() throws IOException;

}
