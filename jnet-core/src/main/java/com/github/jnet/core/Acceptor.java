package com.github.jnet.core;

import com.github.jnet.nio.IoProcessor;

public interface Acceptor extends Runnable {

  void setProcessors(IoProcessor[] processors);
}
