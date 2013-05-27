package com.github.jnet.v2;

/**
 * 会话
 * <p>
 * 包含WriteBuffer,ReadBuffer
 * </p>
 * @author xiebiao
 */
public abstract interface Session {

    public abstract void open();

    public abstract void readCompleted();

    public abstract void reading();

    public abstract void writeCompleted();

    public abstract void writing();

    public abstract void close();
}
