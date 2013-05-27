package com.github.jnet.codec;


public interface Session {

    public void open();

    public void close();

    public boolean isIdle();

    public long getSessionTimeout();

    public void setSessionTimeout(long sessionTimeout);

    public void setEncoder(Encoder encoder);

    public void setDecoder(Decoder decoder);
}
