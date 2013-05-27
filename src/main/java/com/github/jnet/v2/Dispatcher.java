package com.github.jnet.v2;

/**
 * 分发器(单例，线程安全)
 * @author xiebiao
 */
public interface Dispatcher extends Runnable {

    public void dispatch();

    public void registerSession(Session session);

    public void removeSession(Session session);
}
