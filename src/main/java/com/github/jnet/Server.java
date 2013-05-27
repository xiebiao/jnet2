package com.github.jnet;

import java.net.InetSocketAddress;
import java.util.Map;

public interface Server {

    public void setOptions(Map<String, Object> options);

    public void init(InetSocketAddress socketAddress);

    public void start();

    public void stop();
}
