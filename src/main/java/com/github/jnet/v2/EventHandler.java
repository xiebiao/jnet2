package com.github.jnet.v2;

public abstract class EventHandler implements Handler {

    public abstract void read();

    public abstract void write();

    public abstract void close();

    public abstract void accept();

}
