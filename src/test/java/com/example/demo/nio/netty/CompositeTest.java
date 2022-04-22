package com.example.demo.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static com.example.demo.nio.netty.ByteBufTest.log;

public class CompositeTest {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{6, 7, 8, 9, 10});

//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//        buffer.writeBytes(buf1).writeBytes(buf2);
//
//        log(buffer);

        CompositeByteBuf compositeBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        compositeBuf.addComponents(true,buf1,buf2);
        log(compositeBuf);

    }
}
