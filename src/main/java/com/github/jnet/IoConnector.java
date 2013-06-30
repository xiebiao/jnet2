package com.github.jnet;


public abstract class IoConnector implements Acceptor {

    protected String      name;
    protected Processor[] processors;
    protected int         nextProcessorIndex = 0;

    public IoConnector(String name) {
        this.name = name;
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
