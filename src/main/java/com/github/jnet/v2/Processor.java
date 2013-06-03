package com.github.jnet.v2;

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
}
