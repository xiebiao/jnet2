package com.github.jnet.v3;

public interface Acceptor<T extends ServiceHandler> extends EventHandler {

    public void open(java.net.InetSocketAddress address);

    public void accept();

    public T makeHandler();

    public void acceptHandler(T handler);

    public void activateHandler(T handler);

    public T getHandle();

    public void closeHandler();
}
