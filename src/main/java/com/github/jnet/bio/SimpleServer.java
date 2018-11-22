package com.github.jnet.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleServer {

    private ServerSocket  serverSocket;
    private AtomicBoolean hasRead = new AtomicBoolean(false);

    public SimpleServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("Server started.");
        while (true) {
            /**
             * 轮询是否有连接过来(如果并发连接请求，将导致阻塞)
             */
            Socket socket = serverSocket.accept();
            if (socket == null) {
                continue;
            }
            new Thread(new Worker(socket)).start();
        }
    }

    class Worker implements Runnable {

        private Socket socket;

        Worker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = null;
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                while (reader != null && (line = reader.readLine()) != null && !line.equals("\n")) {
                    System.out.println(line);
                    writer.write(line + "\r\n");
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        SimpleServer server = new SimpleServer(8081);
        server.start();
    }

}
