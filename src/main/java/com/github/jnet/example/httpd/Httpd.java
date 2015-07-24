package com.github.jnet.example.httpd;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.github.jnet.IoAcceptor;
import com.github.jnet.IoProcessor;
import com.github.jnet.factory.AbstractConnectionFactory;

/**
 * @author bjxieb
 * @date 7/24/15
 */
public class Httpd extends IoAcceptor {
  public Httpd(String name, InetSocketAddress address, AbstractConnectionFactory factory)
      throws IOException {
    super(name, address, factory);
  }

  public static void main(String[] args) throws IOException {
    AbstractConnectionFactory factory = new HttpConnectionFactory();
    Httpd acceptor = new Httpd("httpd", new InetSocketAddress("127.0.0.1", 8083), factory);
    IoProcessor[] processors = new IoProcessor[Runtime.getRuntime().availableProcessors()];
    for (int i = 0; i < processors.length; i++) {
      processors[i] = new IoProcessor(i + "");
    }
    // 设置处理器
    acceptor.setProcessors(processors);
    new Thread(acceptor).start();
  }
}
