package com.github.jnet.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.github.jnet.EventHandler;
import com.github.jnet.EventType;

/**
 * 初始化SOCKET
 * @author xiebiao[谢彪]
 * @email xiebiao@jd.com
 */
public class LoggingAcceptor implements EventHandler, Runnable {

    private Selector            selector;
    private ServerSocketChannel serverSocket;

    public LoggingAcceptor(InetSocketAddress socketAddress) {

    }

    @Override
    public void handleEvent(EventType event) {
        // TODO Auto-generated method stub

    }

    @Override
    public EventHandler getHandler() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void run() {
        try {
            SocketChannel socket = serverSocket.accept();
            if (socket != null) {
                // 调用Handler来处理channel
                new LoggingHandler(selector, socket);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
