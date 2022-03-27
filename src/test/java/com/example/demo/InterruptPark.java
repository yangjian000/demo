package com.example.demo;

import java.util.concurrent.locks.LockSupport;

public class InterruptPark {
    public static void main(String[] args) throws InterruptedException {
        InterruptPark interruptPark = new InterruptPark();
        interruptPark.test();
    }

    private void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("park...");
            LockSupport.park();
            System.out.println("unPark...");
            System.out.println("Interrupt State : "+Thread.interrupted());
            System.out.println("Interrupt State : "+Thread.interrupted());
            System.out.println("Interrupt State : "+Thread.currentThread().isInterrupted());
        },"t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();
    }

}
