package com.github.jnet.bio.example.echo;

import com.github.jnet.bio.AbstractServer;
import com.github.jnet.bio.SessionManager;

import java.net.InetSocketAddress;


public class EchoServer extends AbstractServer {

    public EchoServer(InetSocketAddress address) {
        super(address);
    }

    public static final void main(String args[]) {
        SessionManager sm = new SessionManager();
        sm.setHandler(EchoSession.class);
        EchoServer server = new EchoServer(new InetSocketAddress("127.0.0.1", 8080));
        try {
            server.init(sm);
            server.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
