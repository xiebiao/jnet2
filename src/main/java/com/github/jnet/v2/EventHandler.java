package com.github.jnet.v2;

public interface EventHandler extends Handler {

    public void read();

    public void write();

    public void close();

    public void accept();

}
