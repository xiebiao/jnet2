package com.github.jnet;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiebiao
 */
public abstract class Acceptor {

    private static final Logger logger          = LoggerFactory.getLogger(Acceptor.class);
    protected String            name            = "Server";
    private Worker[]            workers;
    private Selector            selector;
    private ServerSocketChannel serverSocket;
    private int                 nextWorkerIndex = 0;
    private SessionManager      sessionManager;
    private ExecutorService     executor;
    private Object              _lock           = new Object();
    protected String            ip;
    protected int               port            = 8081;
    protected int               threads;
    protected int               maxConnection;

    public Acceptor() {

    }

    public abstract void setIp(String ip);

    public abstract void setPort(int port);

    public abstract void setThreads(int threads);

    public abstract void setMaxConnection(int maxConnection);

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 启动服务
     * @throws Exception
     */
    public void start() throws Exception {
        synchronized (_lock) {
            if (this.serverSocket == null) {
                throw new Exception("Server must initialize before start.");
            }
            sessionManager.initialize(this.maxConnection);
            executor = Executors.newFixedThreadPool(this.threads, new JnetThreadFactory());
            for (int i = 0; i < workers.length; i++) {
                workers[i] = new Worker(sessionManager);
                executor.execute(workers[i]);
            }
            logger.info(name + " started : " + this);
            SocketChannel csocket = null;
            try {
                while (true) {
                    if (serverSocket == null) {
                        logger.warn("ServerSocket is not open.");
                        break;
                    }
                    selector.select();// block
                    csocket = serverSocket.accept();
                    csocket.configureBlocking(false);
                    Session session = sessionManager.getSession(csocket);
                    if (session == null) {
                        logger.error("Too many connection.");
                        csocket.close();
                        continue;
                    } else {
                        session.register(csocket);
                        handleNewSession(session);
                    }
                }
            } catch (Exception e) {
                if (csocket != null && csocket.isConnected()) {
                    csocket.close();
                }
                logger.error(name + " running exception:", e);
            }
        }
    }

    public void init(SessionManager sessionManager) throws Exception {
        synchronized (_lock) {
            this.sessionManager = sessionManager;
            workers = new Worker[this.threads];
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();;
            serverSocket.configureBlocking(false);
            if (this.ip == null) {
                throw new java.lang.IllegalStateException("ip can't be null");
            }
            serverSocket.socket().bind(new InetSocketAddress(InetAddress.getByName(this.ip), this.port));
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        }
    }

    public void stop() throws Exception {
        synchronized (_lock) {
            logger.debug(this.name + " stoped.");
            if (null != serverSocket) {
                serverSocket.socket().close();
                serverSocket.close();
                serverSocket = null;
            }
            if (null != this.executor) {
                this.executor.shutdown();
                this.executor = null;
            }
            this.sessionManager.destroy();
        }
    }

    /**
     * 处理一个新session，为其指定一个工作线程，并加入到工作线程新session队列
     * @param session
     */
    private void handleNewSession(Session session) {
        workers[nextWorkerIndex].addNewSession(session);
        nextWorkerIndex = (nextWorkerIndex + 1) % this.threads;
    }

    public String toString() {
        return "{ip:" + this.ip + ", port:" + port + ", threads:" + threads + ", maxConnection:" + maxConnection + "}";
    }
}
