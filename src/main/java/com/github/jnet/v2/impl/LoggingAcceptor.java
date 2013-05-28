package com.github.jnet.v2.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import com.github.jnet.v2.Dispatcher;
import com.github.jnet.v2.EventHandler;
import com.github.jnet.v2.EventType;
import com.github.jnet.v2.IoAcceptor;

public class LoggingAcceptor extends IoAcceptor {

    private InetSocketAddress                    socketAddress;
    private Selector                             selector;
    private ServerSocketChannel                  serverSocketChannel;
    private EventHandler                         handler;
    public final static Dispatcher<EventHandler> dispacher = InitiationDispatcher.getInstance();

    public LoggingAcceptor(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        new Thread(dispacher).start();
        // dispacher.register(this, EventType.ACCEPT_EVENT);
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();// block
                SocketChannel socket = serverSocketChannel.accept();
                Set selected = selector.selectedKeys();
                if (socket != null) {
                    System.out.println(" socket.isConnected()");
                    socket.configureBlocking(false);
                    dispacher.register(handler, EventType.ACCEPT_EVENT);
                }
                selected.clear();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void bind() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();;
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(socketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void register(EventHandler handler) {
        if (handler != null) {
            throw new java.lang.NullPointerException("handler is null");
        }
        this.handler = handler;
    }

}
