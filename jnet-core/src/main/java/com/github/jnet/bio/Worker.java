package com.github.jnet.bio;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.github.jnet.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.utils.IoUtils;

public class Worker implements Runnable {

    private static final Logger logger            = LoggerFactory.getLogger(Worker.class);
    private Selector            selector;
    private SessionManager      sessionManager;
    private Queue<Session>      newSessionQueue   = new ConcurrentLinkedQueue<Session>();
    private List<Session>       eventSessionList  = new ArrayList<Session>();
    private Set<Session>        timeoutSessionSet = new TreeSet<Session>(new Comparator<Session>() {

                                                      public int compare(Session a, Session b) {
                                                          if (a.getNextTimeout() - b.getNextTimeout() == 0) {
                                                              return 0;
                                                          } else if (a.getNextTimeout() - b.getNextTimeout() > 0) {
                                                              return 1;
                                                          } else {
                                                              return -1;
                                                          }
                                                      }
                                                  });

    public Worker(SessionManager sessionManager) throws IOException {
        this.sessionManager = sessionManager;
        this.selector = Selector.open();
    }

    /**
     * 设置超时的session加入到超时队列中
     * @param session
     */
    private void addTimeoutSession(Session session) {
        logger.debug("Session[" + session.getId() + "] is time out.");
        if (session.getNextTimeout() > 0) {
            timeoutSessionSet.add(session);
        }
    }

    public void run() {
        try {
            while (true) {
                // 轮询调度非阻塞请求
                select();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void select() throws IOException {
        long timeout = 0;
        Iterator<Session> sessionIter = timeoutSessionSet.iterator();
        if (sessionIter.hasNext()) {
            Session session = sessionIter.next();
            timeout = session.getNextTimeout() - System.currentTimeMillis();
            timeout = Math.max(timeout, 1);
        }
        selector.select(timeout);
        long now = System.currentTimeMillis();
        handleNewSession();
        checkEventSession(now);
        handleEventSession();
    }

    private void handleNewSession() {
        while (true) {
            Session session = newSessionQueue.poll();
            if (session == null) {
                break;
            }
            initNewSession(session);
        }
    }

    public void addNewSession(Session session) {
        newSessionQueue.add(session);
        selector.wakeup();
    }

    /**
     * 初始化新会话
     * @param session
     */
    private void initNewSession(Session session) {
        try {
            /** 初始化buffer */
            session.readBuffer.position(0);
            session.readBuffer.limit(0);
            session.writeBuffer.position(0);
            session.writeBuffer.limit(0);
            session.open(session.readBuffer, session.writeBuffer);
            updateSession(session);
        } catch (Exception e) {
            close(session);
            e.printStackTrace();
        }

    }

    /**
     * 更新session状态
     * @param session
     * @throws ClosedChannelException
     */
    private void updateSession(Session session) throws ClosedChannelException {
        if (session.getCurrentState() == Session.IoState.READ && session.readBuffer.remaining() > 0) {
            if (this.sessionManager.getReadTimeout() > 0) {
                if (session.getNextTimeout() > System.currentTimeMillis()) {
                    /** 读操作已超时 */
                    session.setNextTimeout(System.currentTimeMillis() + this.sessionManager.getReadTimeout());
                    addTimeoutSession(session);
                }
            }
            /** session注册socket读事件 */
            session.getSocketChannel().register(selector, SelectionKey.OP_READ, session);

        } else if (session.getCurrentState() == Session.IoState.WRITE && session.writeBuffer.remaining() > 0) {
            if (this.sessionManager.getWriteTimeout() > 0) {
                if (session.getNextTimeout() > System.currentTimeMillis()) {
                    /** 写操作已超时 */
                    session.setNextTimeout(System.currentTimeMillis() + this.sessionManager.getWriteTimeout());
                    addTimeoutSession(session);
                }
            }
            /** session注册socket读事件 */
            session.getSocketChannel().register(selector, SelectionKey.OP_WRITE, session);
        } else {
            /** 关闭 session */
            close(session);
        }
    }

    /**
     * @param timeout
     */
    private void checkEventSession(long timeout) {
        eventSessionList.clear();
        Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
        while (keyIter.hasNext()) {
            SelectionKey key = keyIter.next();
            keyIter.remove();
            Session session = (Session) key.attachment();
            eventSessionList.add(session);
        }
        /** 处理timeout */
        Iterator<Session> sessionIter = timeoutSessionSet.iterator();
        while (sessionIter.hasNext()) {
            Session session = sessionIter.next();
            if (session.getNextTimeout() <= timeout) {
                session.setCurrentEvent(Session.Event.TIMEOUT);
                eventSessionList.add(session);
                sessionIter.remove();
            } else {
                break;
            }
        }

    }

    /**
     * 处理当前有事件session 3种事件：可读、可写、超时
     */
    private void handleEventSession() {
        Iterator<Session> eventIter = eventSessionList.iterator();
        while (eventIter.hasNext()) {
            Session session = eventIter.next();
            try {
                if (session.getCurrentEvent() == Session.Event.TIMEOUT) {
                    timeoutEvent(session);
                } else if (session.getCurrentState() == Session.IoState.READ) {
                    readEvent(session);
                } else if (session.getCurrentState() == Session.IoState.WRITE) {
                    writeEvent(session);
                }
            } catch (Exception e) {
                eventIter.remove();
                close(session);
            }
        }
    }

    /**
     * 处理超时事件
     * @param session
     * @throws Exception
     */
    private void timeoutEvent(Session session) {
        try {
            session.timeout();
            updateSession(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ioEvent(Session session, IoBuffer buffer) throws Exception {
        while (buffer.remaining() > 0) {
            int dataLength = 0;
            Session.IoState curState = session.getCurrentState();
            // 根据Session状态
            switch (curState) {
                case READ:
                    dataLength = IoUtils.read(session.getSocketChannel(), buffer);
                    break;
                case WRITE:
                    dataLength = IoUtils.write(session.getSocketChannel(), buffer);
                    break;
                case CLOSE:
                    break;
            }
            // 还有多少数据未处理
            int remain = buffer.remaining();
            switch (curState) {
                case READ:
                    if (remain == 0) {
                        session.setNextState(Session.IoState.CLOSE);
                    } else {
                        session.read(session.readBuffer, session.writeBuffer);
                    }
                    break;
                case WRITE:
                    if (remain == 0) {
                        session.setNextState(Session.IoState.CLOSE);
                    } else {
                        session.write(session.readBuffer, session.writeBuffer);
                    }
                    break;
                case CLOSE:
                    break;
            }

            if (dataLength == 0 || session.getCurrentState() != curState) {
                updateSession(session);
                break;
            }
        }
    }

    /**
     * 处理读事件
     * @param session
     * @throws Exception
     */
    public void readEvent(Session session) throws Exception {
        ioEvent(session, session.readBuffer);
    }

    /**
     * 处理写事件
     * @param session
     * @throws ClosedChannelException
     */
    public void writeEvent(Session session) throws Exception {
        ioEvent(session, session.writeBuffer);
    }

    /**
     * 关闭session 用于清理
     * @param session
     */
    private void close(Session session) {
        if (session.getSocketChannel() != null) {
            try {
                if (session.getSocketChannel().keyFor(selector) != null) {
                    session.getSocketChannel().keyFor(selector).cancel();
                }
                if (session.getSocketChannel().isConnected()) {
                    session.getSocketChannel().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            session.setSocketChannel(null);
        }
        timeoutSessionSet.remove(session);
        session.close();
        session.setIdle(false);
    }
}
