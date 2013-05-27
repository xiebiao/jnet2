package com.github.jnet;

import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.buffer.IoBuffer;

public abstract class Session {

    private static final Logger logger      = LoggerFactory.getLogger(Session.class);

    protected int               id          = 0;                                     // 会话id

    protected long              nextTimeout = 0;                                     // 下一次超时时间

    public enum IoState {
        READ, WRITE, CLOSE
    }

    protected IoState currentState; // 当前IO状态

    public enum Event {
        READ, WRITE, TIMEOUT
    }

    protected Event         currentEvent = Event.READ; // 当前会话事件

    protected IoBuffer      readBuffer   = null;

    protected IoBuffer      writeBuffer  = null;

    protected SocketChannel channel       = null;

    private boolean         idle         = false;

    public Session() {
        id = 0;
    }

    /*------------------------------------------------------------------ change state */
    public void timeout() throws Exception {
        logger.debug("The Session " + this.getId() + " is timeout, will be closed.");
        setNextState(IoState.CLOSE);
    }

    public final void setNextState(IoState state) {
        this.currentState = state;
        switch (state) {
            case WRITE:
                readBuffer.position(0);
                readBuffer.limit(0);
                break;
            case READ:
                writeBuffer.position(0);
                writeBuffer.limit(0);
                break;
            case CLOSE:
                this.close();
        }
        switch (state) {
            case READ:
                logger.info("Set the Session[" + this.getId() + "] : currentState = " + "STATE_READ.");
                break;
            case WRITE:
                logger.info("Set the Session[" + this.getId() + "] : currentState = " + "STATE_WRITE.");
                break;
            case CLOSE:
                logger.info("Set Session[" + this.getId() + "] : currentState = " + "STATE_CLOSE.");
                break;
        }
    }

    public void remain(int remain, IoState state) {
        switch (state) {
            case READ:
                readBuffer.limit(readBuffer.position() + remain);
                setNextState(IoState.READ);
                break;
            case WRITE:
                writeBuffer.limit(writeBuffer.position() + remain);
                setNextState(IoState.WRITE);
                break;
        }

    }

    /*------------------------------------------------------------------ abstract methods */
    public abstract void open(IoBuffer readBuf, IoBuffer writeBuf) throws Exception;

    public abstract void readCompleted(IoBuffer readBuf, IoBuffer writeBuf) throws Exception;

    public abstract void reading(IoBuffer readBuf, IoBuffer writeBuf) throws Exception;

    public abstract void writeCompleted(IoBuffer readBuf, IoBuffer writeBuf) throws Exception;

    public abstract void writing(IoBuffer readBuf, IoBuffer writeBuf) throws Exception;

    public abstract void close();

    /*------------------------------------------------------------------ getter/setter */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event event) {
        this.currentEvent = event;
    }

    public void setReadBuffer(IoBuffer readBuf) {
        this.readBuffer = readBuf;
    }

    public SocketChannel getSocket() {
        return channel;
    }

    public void register(SocketChannel channel) {
        this.channel = channel;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public void setWriteBuffer(IoBuffer writeBuffer) {
        this.writeBuffer = writeBuffer;
    }

    public long getNextTimeout() {
        return nextTimeout;
    }

    public void setNextTimeout(long nextTimeout) {
        this.nextTimeout = nextTimeout;
    }

    public IoState getCurrentState() {
        return currentState;
    }

}
