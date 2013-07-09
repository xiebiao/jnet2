package com.github.jnet;

import java.io.IOException;

public final class IoProcessor {

    private Reactor reactor;
    public IoProcessor(String name) {
        try {
            reactor = new Reactor(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(Connection connection) {
        reactor.read(connection);
    }

    public void write(Connection connection) {
        reactor.write(connection);
    }
}
