package com.github.jnet.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.jnet.buffer.IoBuffer;
import com.github.jnet.core.Connection;

public abstract class AbstractConnection implements Connection {

  protected final SocketChannel channel;
  protected SelectionKey processKey;
  protected IoProcessor processor;
  protected IoBuffer readBuffer = null;
  protected IoBuffer writeBuffer = null;
  protected AtomicBoolean isRegistered;
  protected AtomicBoolean isClosed;
  protected int bufferMaxSize = 1024;
  protected IoHandler handler;

  // private static final Logger logger =
  // LoggerFactory.getLogger(AbstractConnection.class);

  public AbstractConnection(SocketChannel channel) throws IOException {
    this.channel = channel;
    readBuffer = new IoBuffer();
    readBuffer.position(0);
    readBuffer.limit(0);

    writeBuffer = new IoBuffer();
    writeBuffer.position(0);
    writeBuffer.limit(0);
    isRegistered = new AtomicBoolean(false);
    isClosed = new AtomicBoolean(false);
  }

  public void setHandler(IoHandler handler) {
    this.handler = handler;
  }

  public void setProcessor(IoProcessor processor) {
    this.processor = processor;
  }

  @Override
  public void register(Selector selector) throws IOException {
    if (isRegistered.get() == false) {
      processKey = channel.register(selector, SelectionKey.OP_READ, this);
      isRegistered.getAndSet(true);
    }
  }

}
