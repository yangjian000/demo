package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class OrderExecTest {

    static final Object lock = new Object();

    static boolean t2runned = false;

    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
            LockSupport.unpark(t2);
        });

        t2 = new Thread(() -> {
            LockSupport.park();
            log.debug("2");
            LockSupport.unpark(t3);
        });
        t3 = new Thread(() -> {
            LockSupport.park();
            log.debug("3");
//            LockSupport.unpark(t1);
        });

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);

    }

    private static void test1() {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2runned) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2runned = true;
                lock.notify();

            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
