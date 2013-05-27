package com.github.jnet;

/**
 * 分发器(单例，线程安全)
 * @author xiebiao
 */
public interface Dispatcher {

    /**
     * 处理事件
     */
    public void handleEvent();

    /**
     * 注册是事件响应处理器
     * @param handler
     * @param event
     */
    public void registerHandler(EventHandler handler, EventType event);

    /**
     * 删除时间响应处理器
     * @param handler
     * @param event
     */
    public void removeHandler(EventHandler handler, EventType event);
}
