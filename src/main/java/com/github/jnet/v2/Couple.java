package com.github.jnet.v2;

public class Couple<K, V> {

    private K evnetType;
    private V handler;

    public Couple(K eventType, V handler) {
        this.evnetType = eventType;
        this.handler = handler;
    }

    public K getEventType() {
        return evnetType;
    }

    public V getHandler() {
        return handler;
    }

}
