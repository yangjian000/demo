package com.example.demo.nio.jdk;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteBufferExam {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(buffer);
        buffer.put("w are you?\n".getBytes());
        split(buffer);

    }

    public static void split(ByteBuffer buffer) {
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            if (buffer.get(i) == '\n') {
                int length = i + 1 - buffer.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(buffer.get());
                }
                target.flip();
                System.out.print(StandardCharsets.UTF_8.decode(target));
            }
        }
        buffer.compact();
    }
}
