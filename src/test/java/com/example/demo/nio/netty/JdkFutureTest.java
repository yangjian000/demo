package com.example.demo.nio.netty;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class JdkFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Integer> future = service.submit(() -> {
            log.debug("execute ...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        log.debug("waiting ...");
        Integer integer = future.get();
        log.debug("{}",integer);
    }
}
