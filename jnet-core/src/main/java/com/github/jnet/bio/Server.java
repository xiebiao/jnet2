package com.github.jnet.bio;

import java.io.IOException;
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
public abstract class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    protected String name = "Server";
    private Worker[] workers;
    private Selector selector;
    private ServerSocketChannel serverSocket;
    private int nextWorkerIndex = 0;
    private SessionManager sessionManager;
    private ExecutorService executor;
    private Object _lock = new Object();
    protected InetSocketAddress socketAddress;
    private int threads;
    protected int maxConnection;

    public Server(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        threads = Runtime.getRuntime().availableProcessors();
    }

    public abstract void setMaxConnection(int maxConnection);

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 启动服务
     *
     * @throws Exception
     */
    public void start() throws Exception {
        synchronized (_lock) {
            if (this.serverSocket == null) {
                throw new Exception("Server must initialize before start.");
            }
            setWorkers();
            logger.info(name + " started : " + this);
            SocketChannel csocket = null;
            try {
                while (true) {
                    if (serverSocket == null) {
                        logger.warn("ServerSocket is not open.");
                        break;
                    }
                    selector.select(1000L);// block
                    csocket = serverSocket.accept();
                    if (csocket == null)
                        continue;
                    csocket.configureBlocking(false);
                    Session session = sessionManager.getSession();
                    if (session == null) {
                        logger.error("Too many connection.");
                        csocket.close();
                        continue;
                    } else {
                        session.setSocketChannel(csocket);
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

    private void setWorkers() throws SessionException, IOException {
        sessionManager.initialize(this.maxConnection);
        executor = Executors.newFixedThreadPool(this.threads, new JnetThreadFactory());
        workers = new Worker[this.threads];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker(sessionManager);
            executor.execute(workers[i]);
        }
    }

    public void init(SessionManager sessionManager) throws Exception {
        synchronized (_lock) {
            this.sessionManager = sessionManager;
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.configureBlocking(false);
            serverSocket.socket().bind(this.socketAddress);
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
     *
     * @param session
     */
    private void handleNewSession(Session session) {
        workers[nextWorkerIndex].addNewSession(session);
        nextWorkerIndex = (nextWorkerIndex + 1) % this.threads;
    }

    public String toString() {
        return "{ip:" + this.socketAddress.getHostName() + ", port:" + this.socketAddress.getPort()
            + ", threads:"
            + threads + ", maxConnection:" + maxConnection + "}";
    }
}
