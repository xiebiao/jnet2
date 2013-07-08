package com.github.jnet;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class IoConnector implements Acceptor {

    protected String         name;
    protected Processor[]    processors;
    protected final Selector selector;
    protected int            nextProcessorIndex = 0;
    protected LinkedBlockingQueue<Connection> connectQueue = new LinkedBlockingQueue<Connection>();
    
    public IoConnector(String name) throws IOException {
        this.name = name;
        this.selector = Selector.open();
    }

    @Override
    public void run() {

    }

    @Override
    public void setProcessors(Processor[] processors) {
        this.processors = processors;

    }

    protected Processor getNextProcessor() {
        this.nextProcessorIndex = (this.nextProcessorIndex + 1) % this.processors.length;
        return this.processors[this.nextProcessorIndex];
    }
}
