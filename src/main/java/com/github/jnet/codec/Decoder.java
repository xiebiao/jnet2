package com.github.jnet.codec;

import com.github.jnet.buffer.IoBuffer;

public interface Decoder {

    public Object decode(IoBuffer buffer, Session session);
}
