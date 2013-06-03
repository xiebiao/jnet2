package com.github.jnet;

/**
 * 接收器
 * @author xiebiao
 */
public interface Acceptor extends Runnable {

    public void setProcessors(Processor[] processors);
    public void setHandlerFactory(AbstractConnectionFactory factory);
}
