package com.github.jnet;

import com.github.jnet.codec.Decoder;
import com.github.jnet.codec.Encoder;

public interface Session {

    public void open();

    public void close();

    public boolean isIdle();

    public long getSessionTimeout();

    public void setSessionTimeout(long sessionTimeout);

    public void setEncoder(Encoder encoder);

    public void setDecoder(Decoder decoder);
}
