package com.example.demo.jvm;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;


/**
 * -Xmx20m -XX:+PrintGCDetails -verbose:gc
 */
public class SoftReferenceTest {

    static int _4M = 4 * 1024 * 1024;

    public static void main(String[] args) throws IOException {
/*        ArrayList<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new byte[_4M]);
        }
        System.in.read();*/
        soft();
    }

    public static void soft() {

        ArrayList<SoftReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SoftReference<byte[]> ref = new SoftReference<>(new byte[_4M]);
            System.out.println(ref.get());
            list.add(ref);
            System.out.println(list.size());
        }
        System.out.println("list size:" + list.size());
        for (SoftReference<byte[]> ref : list) {
            System.out.println(ref.get());
        }
    }
}
