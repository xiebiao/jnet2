package com.github.jnet.v3.impl;

import java.net.InetSocketAddress;

import com.github.jnet.v3.Acceptor;
import com.github.jnet.v3.EventHandler;

public class NIOAcceptor implements Acceptor<SimpleServiceHandler> {

    @Override
    public void open(InetSocketAddress address) {
        // TODO Auto-generated method stub

    }

    @Override
    public void accept() {
        // TODO Auto-generated method stub

    }

    @Override
    public SimpleServiceHandler makeHandler() {
        // TODO Auto-generated method stub
        return new SimpleServiceHandler();
    }

    @Override
    public void acceptHandler(SimpleServiceHandler handler) {
        // TODO Auto-generated method stub

    }

    @Override
    public void activateHandler(SimpleServiceHandler handler) {
        // TODO Auto-generated method stub

    }

    @Override
    public SimpleServiceHandler getHandle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void closeHandler() {
        // TODO Auto-generated method stub

    }

}
