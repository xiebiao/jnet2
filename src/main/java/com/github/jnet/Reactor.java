package com.github.jnet;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Reactor {

    private static final Logger LOG = LoggerFactory.getLogger(Reactor.class);
    private Reader              _reader;
    private Writer              _writer;

    public Reactor(String name) throws IOException {
        _reader = new Reader();
        _writer = new Writer();
        new Thread(_reader, name + "-reader").start();
        new Thread(_writer, name + "-writer").start();
    }

    public final void read(Connection connection) {
        LOG.debug("注册连接到读队列");
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

        private LinkedBlockingQueue<Connection> readQueue = new LinkedBlockingQueue<Connection>();
        private final Selector                  selector;

        public Reader() throws IOException {
            selector = Selector.open();
        }

        private void register(Selector selector) {
            Connection c = null;
            while ((c = readQueue.poll()) != null) {
                try {
                    LOG.debug(Thread.currentThread().getName() + ":从读队列中取出连接");
                    c.register(selector);
                } catch (Throwable e) {

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
                        LOG.debug(Thread.currentThread().getName() + ":遍历网络事件");
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
                System.out.println("write runing");
                if (c != null) {
                    c.write();
                } else {
                    synchronized (this) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }
}
