package com.example.demo;

/**
 * 演示--成员变量线程安全性操作
 * 使用synchronize关键字进行保护
 */
public class ShareResourceProblem {

    static int counter = 0;

    private static final Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        protectBySyncWithOOP();
    }


    /**
     * 使用面对对象优化保护成员变量
     */
    private static void protectBySyncWithOOP() throws InterruptedException {
        Room room = new Room();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.incrementByOne();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrementByOne();
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(room.getCounter());
    }

    /**
     * 使用同步锁保护共享变量
     */
    private static void protectBySync() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (object) {
                    counter++;
                }
            }
        }, "t1");


        Thread t2 = new Thread(() -> {
            synchronized (object) {
                for (int i = 0; i < 5000; i++) {
                    counter--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter);
    }

    /**
     * 线程不安全操作
     */
    private static void noProtect() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter++;
            }

        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter--;
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter);
    }
}

class Room {

    private int counter = 0;

    public void incrementByOne() {
        synchronized (this) {
            counter++;
        }
    }

    public void decrementByOne() {
        synchronized (this) {
            counter--;
        }
    }

    public int getCounter() {
        synchronized (this) {
            return counter;
        }
    }

}

/**
 *
 * 锁定当前类实例对象
 * */
class Test01 {

    public synchronized void test() {
    }

    public void test1() {
        synchronized (this) {
        }
    }
}

/**
 *
 * 锁定当前类所有实例对象
 * */
class Test02 {

    public synchronized static void test() {
    }

    public static void test1() {
        synchronized (Test02.class) {
        }
    }
}