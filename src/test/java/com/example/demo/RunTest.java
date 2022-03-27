package com.example.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RunTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                super.run();
            }
        };
    }

    private static void interrupterTest() throws InterruptedException {

        Thread t1 = new Thread("t1"){
            @SneakyThrows
            @Override
            public void run() {
                log.debug("entry sleeping...");
                Thread.sleep(2_000);
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        log.debug("interrupted...");
        t1.interrupt();
    }



}
