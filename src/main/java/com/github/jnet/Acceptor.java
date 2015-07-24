package com.github.jnet;

public interface Acceptor extends Runnable {

  void setProcessors(IoProcessor[] processors);
}
