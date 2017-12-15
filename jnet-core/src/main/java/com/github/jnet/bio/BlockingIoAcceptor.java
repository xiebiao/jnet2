package com.github.jnet.bio;

import com.github.jnet.core.Acceptor;
import com.github.jnet.nio.IoProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

/**
 * @author bjxieb
 * @date 2017-12-15
 */
public class BlockingIoAcceptor implements Acceptor {
    private static final Logger logger = LoggerFactory.getLogger(BlockingIoAcceptor.class);
    protected InetSocketAddress socketAddress;
    protected ServerSocket serverSocket;

    public BlockingIoAcceptor(InetSocketAddress address) throws IOException {
        this.socketAddress = address;
        //windows supported AIO
        //AsynchronousServerSocketChannel
        serverSocket = new ServerSocket(address.getPort());
        if (logger.isDebugEnabled()) {
            logger.debug("Start listening :" + socketAddress);
        }
    }

    @Override
    public void setProcessors(IoProcessor[] processors) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket sc = serverSocket.accept();
                if (sc != null) {
                    new Thread(new Handler(sc)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Handler implements Runnable {
        private String clientId = UUID.randomUUID().toString();
        private Socket sc;

        public Handler(Socket sc) {
            this.sc = sc;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(sc.getInputStream()));
                String line = null;
                PrintWriter writer = new PrintWriter(sc.getOutputStream());
                while (reader != null && (line = reader.readLine()) != null && !line.equals("\n")) {
                    System.out.println(clientId + ":" + line);
                    writer.write(line + "\r\n");
                    writer.flush();
                }
                sc.close();
            } catch (IOException ex) {

            }
        }
    }
}
