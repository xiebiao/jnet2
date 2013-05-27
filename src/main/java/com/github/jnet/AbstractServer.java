package com.github.jnet;

/**
 * @author xiebiao
 */
public abstract class AbstractServer extends Acceptor {

    public AbstractServer() {
        this.threads = 10;
        this.maxConnection = 10;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;

    }

    @Override
    public void setPort(int port) {
        this.port = port;

    }

    @Override
    public void setThreads(int threads) {
        this.threads = threads;
    }

    @Override
    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

}
