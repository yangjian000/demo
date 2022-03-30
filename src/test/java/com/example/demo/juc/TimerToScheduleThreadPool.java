package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TimerToScheduleThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        pool.scheduleAtFixedRate(()->{
            log.debug("running....");
        },1,2,TimeUnit.SECONDS);

        pool.scheduleWithFixedDelay(()->{
            log.debug("with running ...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1,1,TimeUnit.SECONDS);
    }

    private static void test1() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        pool.schedule(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("task 1");
        }, 1, TimeUnit.SECONDS);

        pool.schedule(()->{
            log.debug("task 2");
        },1,TimeUnit.SECONDS);
    }
}
