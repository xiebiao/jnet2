package com.github.jnet;

import com.github.jnet.buffer.IoBuffer;

public interface Session2 {

    public abstract void open(IoBuffer readBuf, IoBuffer writeBuf) throws Exception;

    public abstract void readCompleted(IoBuffer readBuf, IoBuffer writeBuf) throws Exception;

    public abstract void reading(IoBuffer readBuf, IoBuffer writeBuf) throws Exception;

    public abstract void writeCompleted(IoBuffer readBuf, IoBuffer writeBuf) throws Exception;

    public abstract void writing(IoBuffer readBuf, IoBuffer writeBuf) throws Exception;

    public abstract void close();

}
