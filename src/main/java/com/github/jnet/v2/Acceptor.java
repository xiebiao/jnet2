package com.github.jnet.v2;

/**
 * 接收器
 * @author xiebiao
 * @param <T>
 */
public interface Acceptor<T extends Handler> extends Runnable {

    public void register(T handler);
}
