package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.PhilosopherEatProblem")
public class PhilosopherEatProblem {

    public static void main(String[] args) {
        Fork f1 = new Fork("1");
        Fork f2 = new Fork("2");
        Fork f3 = new Fork("3");
        Fork f4 = new Fork("4");
        Fork f5 = new Fork("5");

        new Philosopher("柏拉图",f1,f2).start();
        new Philosopher("苏格拉底",f2,f3).start();
        new Philosopher("亚里士多德",f3,f4).start();
        new Philosopher("赫拉克利特",f4,f5).start();
        new Philosopher("阿基米德",f5,f1).start();

    }

}

@Slf4j(topic = "c.philosopher")
class Philosopher extends Thread {
    String name;
    Fork left;
    Fork right;
    Random random = new Random();

    public Philosopher(String name, Fork left, Fork right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }


    @Override
    public void run() {
        while (true) {
            if (left.tryLock()) {
                try {
                    if (right.tryLock()) {
                        try {
                            try {
                                eat();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();
                }
            }
        }
    }

    private void eat() throws InterruptedException {
        log.debug(this.name+" eating...");
        TimeUnit.SECONDS.sleep(1);
    }
}

class Fork extends ReentrantLock {

    String name;

    public Fork(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fork{" +
                "name='" + name + '\'' +
                '}';
    }
}