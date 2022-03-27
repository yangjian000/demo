package com.example.demo.nio.netio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NonBlockIOServer {

    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);  // 设置非阻塞模式
        ssc.bind(new InetSocketAddress(8080));

        List<SocketChannel> channels = new ArrayList<>();
        while (true) {

            log.debug("connecting...");
            SocketChannel sc = ssc.accept();  // 不会阻塞 线程还会继续运行 若没有建立连接则sc返回null

            if (sc != null) {
                log.debug("connected {}", sc);
                sc.configureBlocking(false);  // 非阻塞模式
                channels.add(sc);
            }
            for (SocketChannel channel : channels) {
                log.debug("before read {}", channel);
                int read = channel.read(buffer);  // 不会阻塞 线程还会继续运行 如果没有读到数据则read返回0
                if (read > 0) {
                    buffer.flip();
                    log.debug(Charset.defaultCharset().decode(buffer).toString());
                    buffer.clear();
                    log.debug("after read {}", channel);
                }
            }
        }
    }
}
class NonBlockClient{

}