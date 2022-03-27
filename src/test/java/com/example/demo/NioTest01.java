package com.example.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.*;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Set;

public class NioTest01 {
    public static void main(String[] args) {
        extracted04();
    }

    private static void extracted04() {
        try {
            int[] ports = new int[5];
            ports[0] = 5000;
            ports[1] = 5001;
            ports[2] = 5002;
            ports[3] = 5003;
            ports[4] = 5004;
            Selector selector = Selector.open();

            for (int i = 0; i < ports.length; i++) {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);

                ServerSocket serverSocket = serverSocketChannel.socket();
                serverSocket.bind(new InetSocketAddress(ports[i]));

                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("Listening port:" + ports[i]);
            }

            while (true) {

                int numbers = selector.select();
                System.out.println("numbers:" + numbers);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("selectionKeys:" + selectionKeys);

                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();

                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);

                        socketChannel.register(selector, SelectionKey.OP_READ);

                        iterator.remove();

                        System.out.println("获取到客户端连接" + socketChannel);

                    } else if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                        int byteRead = 0;

                        while (true){
                            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                            byteBuffer.clear();
                            int read = socketChannel.read(byteBuffer);
                            if (read<=0){
                                break;
                            }
                            byteBuffer.flip();
                            socketChannel.write(byteBuffer);
                            byteRead+=read;
                        }
                        iterator.remove();
                        System.out.println("读取:"+byteRead + ",来自:"+socketChannel);
                    }

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void extracted03() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("test03.txt");
            FileChannel fileChannel = fileOutputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(512);

            byte[] bytes = "hello world, nio!".getBytes();

            for (int i = 0; i < bytes.length; i++) {
                buffer.put(bytes[i]);
            }

            buffer.flip();
            fileChannel.write(buffer);

            fileChannel.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extracted02() {
        try {
            FileInputStream fileInputStream = new FileInputStream("Test02.txt");
            FileChannel fileChannel = fileInputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(512);

            fileChannel.read(buffer);

            buffer.flip();

            while (buffer.remaining() > 0) {
                System.out.print((char) buffer.get());
            }

            fileChannel.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extracted01() {
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            int randomNum = new SecureRandom().nextInt(20);
            buffer.put(randomNum);
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.print(buffer.get() + " ");
        }
    }
}
