package com.github.jnet.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.core.Acceptor;
import com.github.jnet.factory.AbstractConnectionFactory;

public abstract class IoAcceptor implements Acceptor {

  protected InetSocketAddress socketAddress;
  protected Selector selector;
  protected ServerSocketChannel serverSocketChannel;
  protected IoProcessor[] processors;
  protected int nextProcessorIndex = 0;
  protected AbstractConnectionFactory factory;
  private static final Logger logger = LoggerFactory.getLogger(IoAcceptor.class);

  public IoAcceptor(InetSocketAddress address, AbstractConnectionFactory factory)
      throws IOException {
    this.factory = factory;
    this.socketAddress = address;
    selector = Selector.open();
    //windows supported AIO
    //AsynchronousServerSocketChannel
    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.socket().bind(socketAddress);
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    logger.debug("" + socketAddress);
  }

  public IoAcceptor(String name, InetSocketAddress address, AbstractConnectionFactory factory)
      throws IOException {
    this(address, factory);
    logger.debug("Server " + name + " started: ");
  }

  @Override
  public final void run() {
    while (true) {
      Set<SelectionKey> keys = null;
      try {
        selector.select();// block
        keys = selector.selectedKeys();
        for (SelectionKey key : keys) {
          if (key.isValid() && key.isAcceptable()) {
            accept();
          } else {
            key.cancel();
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        keys.clear();
      }
    }

  }

  private void accept() {

    SocketChannel channel = null;
    try {
      channel = serverSocketChannel.accept();
      channel.configureBlocking(false);
      AbstractConnection connection = factory.create(channel);
      IoProcessor ioProcessor = this.getNextIoProcessor();
      connection.setProcessor(ioProcessor);
      ioProcessor.register(connection);
    } catch (IOException e) {
      // Error handle
      e.printStackTrace();
    }

  }

  @Override
  public void setProcessors(IoProcessor[] processors) {
    this.processors = processors;
  }

  protected IoProcessor getNextIoProcessor() {
    this.nextProcessorIndex = (this.nextProcessorIndex + 1) % this.processors.length;
    return this.processors[this.nextProcessorIndex];
  }

}
