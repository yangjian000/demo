package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CyclicBarrierTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            log.debug("task1 task2 finish ...");
        });

        for (int i = 0; i < 3; i++) {
            service.submit(() -> {
                try {
                    log.debug("task1 begin ...");
                    TimeUnit.SECONDS.sleep(1);
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            service.submit(() -> {
                try {
                    log.debug("task2 begin ...");
                    barrier.await();
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();

    }
}
