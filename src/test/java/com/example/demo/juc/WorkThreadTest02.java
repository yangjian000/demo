package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class WorkThreadTest02 {
    private static final List<String> MENU = Arrays.asList("宫保鸡丁", "辣子鸡", "佛跳墙", "烧烤", "茅台");
    static Random random = new Random();

    static String cooking() {
        return MENU.get(random.nextInt(MENU.size()));
    }

    public static void main(String[] args) {
        ExecutorService waiterPool = Executors.newFixedThreadPool(1);
        ExecutorService cookerPool = Executors.newFixedThreadPool(1);
        waiterPool.execute(() -> {
            log.debug("deal with cooker");
            Future<?> future = cookerPool.submit(() -> {
                log.debug("cooking...");
                return cooking();
            });
            try {
                log.debug("take out: {}", future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        });
        waiterPool.execute(() -> {
            log.debug("deal with cooker");
            Future<?> future = cookerPool.submit(() -> {
                log.debug("cooking...");
                return cooking();
            });
            try {
                log.debug("take out: {}", future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        });
    }

}
