package com.github.jnet.client;

import com.github.jnet.core.Acceptor;
import com.github.jnet.nio.IoProcessor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class IoConnector implements Acceptor {

  protected String name;
  protected IoProcessor[] processors;
  protected final Selector selector;
  protected int nextProcessorIndex = 0;
  protected LinkedBlockingQueue<TargetConnection> connectQueue =
      new LinkedBlockingQueue<TargetConnection>();

  public IoConnector(String name) throws IOException {
    this.name = name;
    this.selector = Selector.open();
  }

  public void holdConnect(TargetConnection c) {
    connectQueue.add(c);
    // connectQueue.offer(e)
    selector.wakeup();
  }

  private void connect(Selector selector) {
    TargetConnection c = null;
    while ((c = connectQueue.poll()) != null) {
      try {
        System.out.println("selector");
        c.connect(selector);
      } catch (Throwable e) {
        //
      }
    }
  }

  @Override
  public void run() {
    final Selector selector = this.selector;
    while (true) {

      try {
        // selector.select(1000L);
        connect(selector);
        Set<SelectionKey> keys = selector.selectedKeys();
        try {
          for (SelectionKey key : keys) {
            Object att = key.attachment();
            if (att != null && key.isValid() && key.isConnectable()) {
              System.out.println("isConnectable");
              closeConnect(key, att);
            } else {
              key.cancel();
            }
          }
        } finally {
          keys.clear();
        }
      } catch (Throwable e) {
        // LOGGER.warn(name, e);
      }
    }
  }

  private void closeConnect(SelectionKey key, Object att) {
    TargetConnection c = (TargetConnection) att;
    try {
      if (c.close()) {
        // clearSelectionKey(key);
        // c.setId(ID_GENERATOR.getId());
        IoProcessor processor = getNextProcessor();
        c.setProcessor(processor);
        // processor.postRegister(c);
      }
    } catch (Throwable e) {
      // clearSelectionKey(key);
      // c.error(ErrorCode.ERR_FINISH_CONNECT, e);
    }
  }

  @Override
  public void setProcessors(IoProcessor[] processors) {
    this.processors = processors;

  }

  protected IoProcessor getNextProcessor() {
    this.nextProcessorIndex = (this.nextProcessorIndex + 1) % this.processors.length;
    return this.processors[this.nextProcessorIndex];
  }
}
