package com.github.jnet.bio;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.jnet.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 会话管理
 *
 * @author xiebiao
 */
public final class SessionManager {

    private static final Logger        logger      = LoggerFactory.getLogger(SessionManager.class);
    private              List<Session> sessionList = new ArrayList<Session>();
    private volatile     Boolean       lock        = false;
    private              Class<?>      sessionHandler;
    private              long          readTimeout;
    private              long          writeTimeout;

    public SessionManager() {
        readTimeout = writeTimeout = 1000;
    }

    public void setHandler(Class<?> sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public void setReadTimeout(long timeout) {
        this.readTimeout = timeout;
    }

    public long getReadTimeout() {
        return this.readTimeout;
    }

    public void setWriteTimeout(long timeout) {
        this.writeTimeout = timeout;
    }

    public long getWriteTimeout() {
        return this.writeTimeout;
    }

    public Session getSession() {
        synchronized (lock) {
            Iterator<Session> sessionIter = sessionList.iterator();
            while (sessionIter.hasNext()) {
                Session session = sessionIter.next();
                if (!session.isIdle()) {
                    session.setIdle(true);
                    logger.info("Get Session[" + session.getId() + "] from pool.");
                    return session;
                }
            }
        }
        return null;
    }

    public void close(Session session) {
        session.setIdle(false);
        logger.info("Session[" + session.getId() + "] " + "is idle.");
    }

    public void destroy() {
        synchronized (lock) {
            for (int i = 0; i < sessionList.size(); i++) {
                Session session = sessionList.remove(i);
                session.setNextState(Session.IoState.CLOSE);
                try {
                    SocketChannel s = session.getSocketChannel();
                    if (s != null && s.isOpen()) {
                        s.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                session = null;
            }
            sessionList = null;
            logger.debug("Session pool is destroyed.");
        }
    }

    public void initialize(int capacity) throws SessionException {
        synchronized (lock) {
            if (sessionList != null && sessionList.size() == capacity) {
                throw new IllegalStateException("Session pool has be initialized.");
            }
            try {
                for (int i = 0; i < capacity; i++) {
                    Object obj = sessionHandler.newInstance();
                    Session session = (Session) obj;
                    session.setId(i);
                    session.setCurrentEvent(Session.Event.READ);
                    session.setIdle(false);
                    session.setReadBuffer(new IoBuffer());
                    session.setWriteBuffer(new IoBuffer());
                    sessionList.add(session);
                }
            } catch (Exception e) {
                throw new SessionException("session", e);
            }
        }
    }
}
