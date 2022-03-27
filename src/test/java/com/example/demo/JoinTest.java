package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class JoinTest {

    static int r = 0;

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    private static void test1() throws InterruptedException {
        log.debug("beginning....");

        Thread t1 = new Thread(() -> {

            log.debug("t1 beginning...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t1 ending...");
            r = 10;

        }, "t1");

        t1.start();
        t1.join();
        log.debug("result:{}", r);
        log.debug("ending...");
    }

}
