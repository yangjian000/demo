package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "lock.test")
public class ReentrantLockTest02 {

    static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("try acquire lock ...");
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("no acquire lock ...");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            try {
                log.debug("acquire lock ...");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("acquire lock ...");
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        lock.unlock();
        log.debug("release lock ...");

    }

    private static void test01() {
        Thread t1 = new Thread(() -> {
            log.debug("try acquire lock ..");
            if (!lock.tryLock()) {
                log.debug("not acquire lock");
                return;
            }

            try {
                log.debug("acquire lock");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("acquire lock");
        t1.start();
    }
}
