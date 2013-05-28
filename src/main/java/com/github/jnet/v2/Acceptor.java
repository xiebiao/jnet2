package com.github.jnet.v2;

public interface Acceptor<T extends Handler> extends Runnable {

    public void register(T handler);
}
