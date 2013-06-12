package com.github.jnet;

import java.io.IOException;

/**
 * 处理器
 * @author xiebiao
 */
public final class Processor {

    private Reactor reactor;
    private String name;
    public Processor(String name) {
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
