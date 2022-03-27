package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;

@Slf4j
public class TestBiased {

    public static void main(String[] args) {
        Vector<Cat> list = new Vector<>();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                Cat cat = new Cat();
                list.add(cat);
                synchronized (cat) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(cat).toPrintable());
                }
            }
            synchronized (list) {
                list.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (list) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("====================================================================");
            for (int i = 0; i < 30; i++) {
                Cat cat = list.get(i);
                log.debug(i + "\t" + ClassLayout.parseInstance(cat).toPrintable());
                synchronized (cat) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(cat).toPrintable());
                }
                log.debug(i + "\t" + ClassLayout.parseInstance(cat).toPrintable());
            }
        }, "t2").start();
    }

    private static void test01() {
        Cat c = new Cat();

        new Thread(() -> {

            log.debug(ClassLayout.parseInstance(c).toPrintable());
            synchronized (c) {
                log.debug(ClassLayout.parseInstance(c).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(c).toPrintable());
            synchronized (TestBiased.class) {
                TestBiased.class.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (TestBiased.class) {
                try {
                    TestBiased.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            log.debug(ClassLayout.parseInstance(c).toPrintable());
            synchronized (c) {
                log.debug(ClassLayout.parseInstance(c).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(c).toPrintable());

        }, "t2").start();
    }

}


class Cat {
}