package com.github.jnet;

public class AbstractHandler implements Handler {

    protected AbstractConnection connection;

    public AbstractHandler(AbstractConnection connection) {
        this.connection = connection;

    }

    @Override
    public void handle(byte[] data) {

    }

}
