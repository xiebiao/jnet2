package com.github.jnet.v2;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Reactor implements Runnable {

    private final Selector  selector;
    private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private Acceptor[]      acceptors;

    public Reactor() throws IOException {
        selector = Selector.open();

    }
    public void setAcceptors(Acceptor[] acceptors)throws IOException {
        this.acceptors = acceptors;
        for (Acceptor acceptor : acceptors) {
            acceptor.register(selector);
            executor.execute(acceptor);
        }
    }
    public void handleEvents(){
        
    }
    @Override
    public void run() {       
        while (!Thread.interrupted()) {
            try {
                selector.select();                
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext()) {
                    // 来一个事件 第一次触发一个accepter线程
                    // 以后触发SocketReadHandler
                    dispatch((SelectionKey) (it.next()));
                }
                // selected.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void dispatch(SelectionKey skey) {
        Runnable r = (Runnable) (skey.attachment());
        if (r != null) {
            executor.execute(r);
        }
    }

    public static final void main(String args[]) {

        try {
            Reactor r = new Reactor();
            r.setAcceptors(new Acceptor[] { new Acceptor(new java.net.InetSocketAddress("10.28.162.40", 8080)) });
            new Thread(r).start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
