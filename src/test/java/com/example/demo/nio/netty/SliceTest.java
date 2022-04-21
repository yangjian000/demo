package com.example.demo.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.example.demo.nio.netty.ByteBufTest.log;

public class SliceTest {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
        log(buffer);
        ByteBuf f1 = buffer.slice(0, 5);
        f1.retain();
        ByteBuf f2 = buffer.slice(5, 5);
        f2.retain();
        log(f1);
        log(f2);

        System.out.println("====================");

        f1.setByte(0, 'k');
        log(f1);
        log(buffer);

        buffer.release();
        log(f1);

        f1.release();
        f2.release();


    }
}
