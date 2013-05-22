package com.github.jnet.codec;

import com.github.jnet.Session;
import com.github.jnet.buffer.IoBuffer;

public interface Encoder {

    public IoBuffer encode(Object message, Session session);
}
