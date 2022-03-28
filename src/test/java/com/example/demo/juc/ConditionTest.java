package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ConditionTest {

    static ReentrantLock ROOM = new ReentrantLock();
    static Condition waitCigaretteSet = ROOM.newCondition();
    static Condition waitTakeoutSet = ROOM.newCondition();
    private static boolean hasCigarette = false;
    private static boolean hasTakeout = false;

    public static void main(String[] args) throws InterruptedException {
        Thread little_south = new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("has cigarette? {}", hasCigarette);
                while (!hasCigarette) {
                    log.debug("no cigarette,need wait...");
                    waitCigaretteSet.await();
                }
                log.debug("can work ...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                ROOM.unlock();
            }
        }, "little south");

        little_south.start();


        Thread little_girl = new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("has food? {}", hasTakeout);
                while (!hasTakeout) {
                    log.debug("no food,need wait ...");
                    waitTakeoutSet.await();
                }
                log.debug("can eating...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                ROOM.unlock();
            }
        }, "little girl");

        little_girl.start();


        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            ROOM.lock();
            try {
                hasTakeout = true;
                waitTakeoutSet.signal();
            } finally {
                ROOM.unlock();
            }
        }, "food sender").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            ROOM.lock();
            try {
                hasCigarette = true;
                waitCigaretteSet.signal();
            } finally {
                ROOM.unlock();
            }
        }, "cigarette sender").start();


    }

}
