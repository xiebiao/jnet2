package com.github.jnet.v3;

public interface Dispatcher extends Runnable {

    /**
     * 处理连接事件
     */
    public void handleEvents();

    /**
     * 注册多个接收器(处理不同监听端口上的连接)
     * @param acceptors
     */
    public void register(Acceptor[] acceptors);
}
