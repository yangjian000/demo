package com.example.demo.nio.jdk;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteBufferStringTest {

    public static void main(String[] args) {

        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        buffer1.flip();
        System.out.println(StandardCharsets.UTF_8.decode(buffer1).toString());


        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        System.out.println(StandardCharsets.UTF_8.decode(buffer2).toString());


        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
    }
}
