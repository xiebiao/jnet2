package com.github.jnet.example.httpd;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.github.jnet.AbstractConnection;
import com.github.jnet.factory.AbstractConnectionFactory;

/**
 * @author bjxieb
 * @date 7/24/15
 */
public class HttpConnectionFactory extends AbstractConnectionFactory {
  @Override
  protected AbstractConnection getConnection(SocketChannel channel) {
    try {
      HttpConnection httpConnection = new HttpConnection(channel);
      return httpConnection;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
