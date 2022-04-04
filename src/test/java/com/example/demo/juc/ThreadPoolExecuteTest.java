package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j(topic = "c.ThreadPoolExecuteTest")
public class ThreadPoolExecuteTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.execute(()->{
          log.debug("{1}");
        });
        pool.execute(()->{
          log.debug("{2}");
        });
        pool.execute(()->{
          log.debug("{3}");
        });
    }
}
