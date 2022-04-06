package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    public static void main(String[] args) {
        DataContainer dataContainer = new DataContainer();

        new Thread(() -> {
            dataContainer.read();
        }, "t1").start();
        new Thread(() -> {
            dataContainer.read();
        }, "t2").start();
    }
}

@Slf4j(topic = "c.DataContainer")
class DataContainer {

    private Object data;

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.ReadLock rLock = rwLock.readLock();

    private ReentrantReadWriteLock.WriteLock wLock = rwLock.writeLock();


    public Object read() {
        log.debug("acquire read lock ...");
        rLock.lock();
        try {
            log.debug("read.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return data;
        } finally {
            rLock.unlock();
        }
    }

    public void write() {
        log.debug("acquire write lock ...");
        wLock.lock();
        try {
            log.debug("write.");
        } finally {
            wLock.unlock();
        }
    }
}