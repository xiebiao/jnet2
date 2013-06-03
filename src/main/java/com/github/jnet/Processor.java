package com.github.jnet;

import java.io.IOException;

/**
 * 处理器
 * @author xiebiao
 */
public final class Processor {

    private Reactor reactor;

    public Processor() {
        try {
            reactor = new Reactor();
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