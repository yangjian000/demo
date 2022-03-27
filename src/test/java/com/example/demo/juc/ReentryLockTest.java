package com.example.demo.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentryLockTest {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            m1();
        } finally {
            lock.unlock();
        }
    }

    private static void m1() {
        lock.lock();
        try {
            m2();
        } finally {
            lock.unlock();
        }
    }

    private static void m2() {
        lock.lock();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
