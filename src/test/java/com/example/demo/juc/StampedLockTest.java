package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

@Slf4j(topic = "c.StampedLockTest")
public class StampedLockTest {
    public static void main(String[] args) {
        DataContainerStamped data = new DataContainerStamped(1);
        new Thread(() -> {
            data.read(1);
        }, "t1").start();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            data.read(0);
        }, "t2").start();
    }
}

@Slf4j(topic = "c.DataContainerStamped")
class DataContainerStamped {

    private int data;

    private final StampedLock lock = new StampedLock();

    public DataContainerStamped(int data) {
        this.data = data;
    }


    public void write(int newData) {
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);
        try {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.data = newData;
        } finally {
            log.debug("write lock {}", stamp);
            lock.unlock(stamp);
        }
    }

    public int read(int readTime) {
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read lock {}", stamp);
        try {
            TimeUnit.SECONDS.sleep(readTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (lock.validate(stamp)) {
            log.debug("read finished {}", stamp);
            return data;
        }
        log.debug("update to read lock ... {}", stamp);
        try {
            stamp = lock.readLock();
            log.debug("read lock {} ", stamp);
            try {
                TimeUnit.SECONDS.sleep(readTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug(" read finished {}", stamp);
            return data;
        } finally {
            log.debug("read unlock ...");
            lock.unlock(stamp);
        }

    }
}