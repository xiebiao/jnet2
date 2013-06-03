package com.github.jnet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

public interface Connection {

    public void register(Selector selector) throws IOException;

    public void write(ByteBuffer buffer);

    public void write();// 内部写

    public void read() throws IOException;

    public boolean close();

}
