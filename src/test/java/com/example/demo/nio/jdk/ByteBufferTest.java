package com.example.demo.nio.jdk;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteBufferTest {

    public static void main(String[] args) {
        ByteBuffer buff = ByteBuffer.allocate(10);
        buff.put(new byte[]{'a', 'b', 'c', 'd'});

        buff.flip();
        // rewind 从头开始读
        buff.get(new byte[4]);
        buff.rewind();
        System.out.println((char) buff.get());
        System.out.println(StandardCharsets.UTF_8.decode(buff));
        buff.rewind();
        // mark & reset
        // mark 做一个标记，记录position位置，reset是将position重置到mark的位置
        System.out.println((char) buff.get());
        System.out.println((char) buff.get());
        buff.mark();
        System.out.println((char) buff.get());
        System.out.println((char) buff.get());
        buff.reset();
        System.out.println((char) buff.get());
        System.out.println((char) buff.get());
        buff.rewind();
        // get(i) 不会改变读索引的位置
        System.out.println((char) buff.get(3));
    }

}
