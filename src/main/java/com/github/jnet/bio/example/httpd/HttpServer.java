package com.github.jnet.bio.example.httpd;

import java.net.InetSocketAddress;

import com.github.jnet.bio.AbstractServer;
import com.github.jnet.bio.SessionManager;


public class HttpServer extends AbstractServer {

  public HttpServer(InetSocketAddress address) {
    super(address);
  }

  public static final void main(String args[]) {
    HttpServer server = new HttpServer(new InetSocketAddress("127.0.0.1", 8081));
    SessionManager sm = new SessionManager();
    sm.setHandler(HttpSession.class);

    try {
      server.init(sm);
      server.start();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
