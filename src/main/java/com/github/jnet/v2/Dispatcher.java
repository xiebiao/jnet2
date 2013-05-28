package com.github.jnet.v2;

/**
 * 事件分发 (线程安全)
 * @author xiebiao[谢彪]
 * @email xiebiao@jd.com
 * @param <T>
 */
public interface Dispatcher<T extends EventHandler> extends Runnable {

    public void handleEvents();

    public  void remove(T handler, EventType eventType);

    public void register(T handler, EventType eventType);
}
