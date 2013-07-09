package com.github.jnet.example.httpclient;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.github.jnet.IoConnector;
import com.github.jnet.IoProcessor;

public class HttpClient extends IoConnector {

    public HttpClient(String name) throws IOException {
        super(name);
    }

    public static final void main(String args[]) {
        try {
            SocketChannel channel = SocketChannel.open();         
            HttpConnection connection = new HttpConnection(channel);
            connection.setHost("10.28.168.53");
            connection.setPort(80);
            HttpClient httpClient = new HttpClient("test");
            httpClient.holdConnect(connection);
            IoProcessor[] processors = new IoProcessor[Runtime.getRuntime().availableProcessors()];
            for (int i = 0; i < processors.length; i++) {
                processors[i] = new IoProcessor(i + "");
            }
            httpClient.setProcessors(processors);
            new Thread(httpClient).start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}