package com.github.jnet.utils;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.github.jnet.buffer.IoBuffer;

public final class IoUtils {

    // private final static Logger logger =
    // LoggerFactory.getLogger(IoUtils.class);

    /**
     * 网络读操作
     * @param socket
     * @param buf
     * @return
     * @throws Exception
     */
    public static int read(SocketChannel socket, IoBuffer buf) throws IOException {
        int len = socket.read(buf.getBuffer());
        if (len < 0) {
            throw new IOException("IO Error");
        }
        return len;
    }

    /**
     * 网络写操作
     * @param socket
     * @param buf
     * @return
     * @throws Exception
     */
    public static int write(SocketChannel socket, IoBuffer buf) throws IOException {
        int len = socket.write(buf.getBuffer());
        if (len < 0) {
            throw new IOException("IO Error");
        }
        return len;
    }

    public static final void main(String args[]) {
        System.out.println(~1);
    }
}
