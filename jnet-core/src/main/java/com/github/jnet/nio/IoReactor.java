package com.github.jnet.nio;

import com.github.jnet.core.Connection;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public final class IoReactor {

  // private static final Logger logger =
  // LoggerFactory.getLogger(Reactor.class);
  private Reader _reader;
  private Writer _writer;

  public IoReactor(String name) throws IOException {
    _reader = new Reader();
    _writer = new Writer();
    new Thread(_reader, name + "-reader").start();
    new Thread(_writer, name + "-writer").start();
  }

  public final void read(Connection connection) {
    _reader.readQueue.add(connection);
    _reader.selector.wakeup();
  }

  public final void write(Connection connection) {
    _writer.writeQueue.add(connection);
    synchronized (_writer) {
      _writer.notify();
    }
  }

  class Reader implements Runnable {

    private LinkedBlockingQueue<Connection> readQueue = new LinkedBlockingQueue<>();
    private final Selector selector;

    public Reader() throws IOException {
      selector = Selector.open();
    }

    private void register(Selector selector) {
      Connection c = null;
      while ((c = readQueue.poll()) != null) {
        try {
          c.register(selector);
        } catch (IOException e) {
          // Exception handle
          e.printStackTrace();
        }
      }
    }

    @Override
    public void run() {
      final Selector selector = this.selector;
      while (true) {
        try {
          // selector.select(1000L);
          selector.select();
          register(selector);
          Set<SelectionKey> keys = selector.selectedKeys();
          try {
            for (SelectionKey key : keys) {
              Object att = key.attachment();
              if (att != null && key.isValid()) {
                int readyOps = key.readyOps();
                Connection c = (Connection) att;
                if ((readyOps & SelectionKey.OP_READ) != 0) {
                  c.read();
                } else if ((readyOps & SelectionKey.OP_WRITE) != 0) {
                  c.write();
                } else {
                  key.cancel();
                }
              } else {
                key.cancel();
              }
            }
          } finally {
            keys.clear();
          }

        } catch (Exception e) {
          // Exception handle
          e.printStackTrace();
        }
      }
    }
  }

  class Writer implements Runnable {

    private LinkedBlockingQueue<Connection> writeQueue = new LinkedBlockingQueue<Connection>();

    @Override
    public void run() {
      while (true) {
        Connection c = writeQueue.poll();
        if (c != null) {
          c.write();
        } else {
          synchronized (this) {
            try {
              this.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }

        }
      }
    }
  }
}
