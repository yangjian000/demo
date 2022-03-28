package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReentrantLockTest {
    static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("try acquire locker ...");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("not acquire locker ...");
                return;
            }
            try {
                log.debug("acquired locker ...");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        log.debug("interrupt t1");
        t1.interrupt();


    }
}
