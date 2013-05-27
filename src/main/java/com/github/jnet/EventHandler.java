package com.github.jnet;

public interface EventHandler {

    public void handleEvent(EventType event);

    public EventHandler getHandler();
}
