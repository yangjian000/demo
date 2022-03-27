package com.example.demo.nio.netio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BlockIOServer {
    public static void main(String[] args) throws IOException {
        // 单线程nio模式
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            log.debug("connecting....");
            SocketChannel socketChannel = serverSocketChannel.accept();   // accept 阻塞方法  线程停止运行
            log.debug("connected... {}", socketChannel);
            channels.add(socketChannel);
            for (SocketChannel channel : channels) {
                log.debug("before read...{}", channel);
                channel.read(buffer);  // read 阻塞方法 线程停止运行
                buffer.flip();
                log.debug("" + StandardCharsets.UTF_8.decode(buffer));
                buffer.clear();
                log.debug("after read...{}", channel);
            }
        }
    }
}

class BlockClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8080));
        System.out.println("waiting....");
    }
}