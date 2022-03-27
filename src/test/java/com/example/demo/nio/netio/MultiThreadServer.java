package com.example.demo.nio.netio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        Worker worker = new Worker("worker-0");

        while (true) {
            boss.select();
            Iterator<SelectionKey> it = boss.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected:{}", sc.getRemoteAddress());
                    log.debug("before register:{}", sc.getRemoteAddress());
                    worker.register(sc);
//                    sc.register(worker.selector, SelectionKey.OP_READ);
                    log.debug("after register:{}", sc.getRemoteAddress());
                }

            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private final String name;
        private volatile boolean start = false;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                thread = new Thread(this, name);
                thread.start();
                selector = Selector.open();
                start = true;
            }
            queue.add(() -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();
                    Runnable task = queue.poll();
                    if (task != null) {
                        task.run();
                    }
                    Iterator<SelectionKey> workerIt = selector.selectedKeys().iterator();
                    while (workerIt.hasNext()) {
                        SelectionKey workerKey = workerIt.next();
                        workerIt.remove();
                        if (workerKey.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) workerKey.channel();
                            log.debug("read:{}", channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            log.debug("read message:{}", Charset.defaultCharset().decode(buffer));
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


class TestClient {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        sc.write(Charset.defaultCharset().encode("123456789abcdef"));
        System.in.read();
    }
}