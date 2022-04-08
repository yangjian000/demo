package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.CountdownLatchTest")
public class CountdownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);
        String[] all = new String[10];
        Random random = new Random();
        for (int j = 0; j < 10; j++) {
            int k = j;
            service.submit(() -> {
                for (int i = 0; i <= 100; i++) {
                    all[k] = i +"%";
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("\r"+Arrays.toString(all));
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("\n the game start ...");
        service.shutdown();
    }

    private static void test02() {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService pool = Executors.newFixedThreadPool(4);
        pool.submit(() -> {
            log.debug("begin ...");
            sleep(1);
            latch.countDown();
            log.debug("end ...{}", latch.getCount());
        });
        pool.submit(() -> {
            log.debug("begin ...");
            sleep(2);
            latch.countDown();
            log.debug("end ...{}", latch.getCount());
        });
        pool.submit(() -> {
            log.debug("begin ...");
            sleep(3);
            latch.countDown();
            log.debug("end ...{}", latch.getCount());
        });
        pool.submit(() -> {
            log.debug("begin wait ...");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            log.debug("end wait ...{}", latch.getCount());
        });
    }

    private static void test01() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(3);
        new Thread(() -> {
            log.debug("begin ...");
            sleep(1);
            count.countDown();
            log.debug("end ...{}", count.getCount());
        }).start();
        new Thread(() -> {
            log.debug("begin ...");
            sleep(2);
            count.countDown();
            log.debug("end ...{}", count.getCount());
        }).start();
        new Thread(() -> {
            log.debug("begin ...");
            sleep(3);
            count.countDown();
            log.debug("end ...{}", count.getCount());
        }).start();

        log.debug("begin wait ...");
        count.await();
        log.debug("end wait ...");
    }

    public static void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
