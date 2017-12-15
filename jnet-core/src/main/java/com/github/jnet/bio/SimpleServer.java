package com.github.jnet.bio;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleServer {

    public static void main(String[] args) throws IOException {
        BlockingIoAcceptor server = new BlockingIoAcceptor(new InetSocketAddress("127.0.0.1", 8083));
        new Thread(server).start();
    }

}
