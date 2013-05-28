package com.github.jnet.v2.impl;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.github.jnet.v2.EventHandler;
import com.github.jnet.v2.EventType;

public class LoggingHandler extends EventHandler {

    private SocketChannel socket;
    private Selector      selector;
    private EventType     eventType;

    public LoggingHandler(SocketChannel socket) throws IOException {
        this.socket = socket;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof LoggingHandler) {
            LoggingHandler handler = (LoggingHandler) obj;
            if (handler.socket == this.socket) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    @Override
    public void read() {
        // TODO Auto-generated method stub

    }

    @Override
    public void write() {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public void accept() {
        // TODO Auto-generated method stub

    }

}
