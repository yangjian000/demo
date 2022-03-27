package com.example.demo;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class DirectMemoryOOM {


    public static void main(String[] args) {
        ArrayList<ByteBuffer> l = new ArrayList<>();

        int i = 0;

        try {
            for (; ; i++) {
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100 * 1024 * 1024);
                l.add(byteBuffer);
            }
        } finally {
            System.out.println(i);
        }

    }

}
