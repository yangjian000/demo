package com.example.demo.algorithm;

import java.util.concurrent.atomic.AtomicInteger;

public class QPSCounter {
    static AtomicInteger qpsCount = new AtomicInteger(100);
    static volatile long lastSeconds = System.currentTimeMillis() / 1000;

    public static void main(String[] args) {

    }

    public static boolean tryAcquire() {
        long current = System.currentTimeMillis() / 1000;
        if (lastSeconds == current) {
            if (qpsCount.decrementAndGet() == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            lastSeconds = current;
            qpsCount = new AtomicInteger(100);
            return true;
        }
    }
}
