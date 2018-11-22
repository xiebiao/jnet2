package com.github.jnet.nio;

public interface Acceptor extends Runnable {

    void setProcessors(IoProcessor[] processors);
}
