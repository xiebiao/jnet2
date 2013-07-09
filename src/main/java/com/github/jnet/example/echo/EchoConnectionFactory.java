package com.github.jnet.example.echo;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.github.jnet.factory.AbstractConnectionFactory;

public class EchoConnectionFactory extends AbstractConnectionFactory {

    @Override
    protected EchoConnection getConnection(SocketChannel channel) {
        EchoConnection con = null;
        try {
            con = new EchoConnection(channel);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }

}
