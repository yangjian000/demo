package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class SimpleDateFormatTest {

    public static void main(String[] args) {
        test02();
    }

    private static void test03() {
        DateTimeFormatter  stf =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(stf.parse("1991-05-20"));
            }).start();
        }
    }

    private static void test01() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    System.out.println(sdf.parse("1991-05-20"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void test02() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (sdf) {
                    try {
                        System.out.println(sdf.parse("1991-05-20"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
