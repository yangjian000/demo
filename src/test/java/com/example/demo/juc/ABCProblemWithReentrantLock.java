package com.example.demo.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ABCProblemWithReentrantLock {

    public static void main(String[] args) throws InterruptedException {
        AwaitSingle awaitSingle = new AwaitSingle(5);
        Condition a = awaitSingle.newCondition();
        Condition b = awaitSingle.newCondition();
        Condition c = awaitSingle.newCondition();

        new Thread(() -> {
            awaitSingle.print("A", a, b);
        }).start();
        new Thread(() -> {
            awaitSingle.print("B", b, c);
        }).start();
        new Thread(() -> {
            awaitSingle.print("C", c, a);
        }).start();

        TimeUnit.SECONDS.sleep(1);

        awaitSingle.lock();
        try {
            a.signal();
        } finally {
            awaitSingle.unlock();
        }

    }

}

class AwaitSingle extends ReentrantLock {

    private int loopNumber;

    public AwaitSingle(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str, Condition cur, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                cur.await();
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}