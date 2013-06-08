package com.github.jnet;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Reactor {

    private static final Logger LOG = LoggerFactory.getLogger(Reactor.class);
    private Read                _read;
    private Write               _write;

    public Reactor() throws IOException {
        _read = new Read();
        _write = new Write();
        new Thread(_read).start();
        new Thread(_write).start();
    }

    public final void read(Connection connection) {
        LOG.debug("注册连接到读队列");
        _read.readQueue.add(connection);
        _read.selector.wakeup();
    }

    public final void write(Connection connection) {
        _write.writeQueue.add(connection);
    }

    class Read implements Runnable {

        private ConcurrentLinkedQueue<Connection> readQueue = new ConcurrentLinkedQueue<Connection>();
        private final Selector                    selector;

        public Read() throws IOException {
            selector = Selector.open();
        }

        private void register(Selector selector) {
            Connection c = null;
            while ((c = readQueue.poll()) != null) {
                try {
                    LOG.debug("从读队列中取出连接");
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
                     selector.select(1000L);
                   // selector.select();
                    register(selector);
                    Set<SelectionKey> keys = selector.selectedKeys();
                    try {
                        // System.out.println("遍历网络事件");
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

    class Write implements Runnable {

        private ConcurrentLinkedQueue<Connection> writeQueue = new ConcurrentLinkedQueue<Connection>();

        @Override
        public void run() {
            while (true) {
                Connection c = writeQueue.poll();
                if (c != null) {
                    c.write();
                }
            }
        }
    }
}
