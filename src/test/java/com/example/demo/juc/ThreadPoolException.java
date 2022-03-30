package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Slf4j
public class ThreadPoolException {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<?> future = pool.submit(() -> {
            log.debug("task");
            int i = 1/0;
            return true;
        });
        log.debug("result: {}",future.get());
    }

    private static void test01() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(() -> {
            try {
                log.debug("task");
                int i = 1 / 0;
            } catch (Exception e) {
                log.error("error:{}", e);
            }
        });
    }
}
