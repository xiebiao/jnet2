package com.github.jnet.v2;

public class Server {

    /**
     * @param args
     */
    public static void main(String[] args) {
        new Thread(new Reactor()).start();

    }

}
