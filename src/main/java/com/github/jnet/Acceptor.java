package com.github.jnet;

public interface Acceptor extends Runnable {

    public void setProcessors(Processor[] processors);
}
