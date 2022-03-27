package com.example.demo;


import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * -Xss1k
 */
@Slf4j
public class StackTest {
    static A a = new A();
    static B b = new B();

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            synchronized(a){
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b){
                    log.debug("t1 a and b ");
                }
            }
        },"t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            synchronized(b){
             synchronized (a){
                 log.debug("t2 a and b");
             }
            }
        },"t2").start();

    }


}

class A {
}

class B {
}