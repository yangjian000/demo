package com.example.demo.juc;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);

        if (now.compareTo(time) > 0) {
            time.plusWeeks(1);
        }
        System.out.println(time);
        long initialDelay = Duration.between(now, time).toMillis();
        System.out.println(initialDelay);
        long period = 1000 * 60 * 60 * 24 * 7;
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(() -> {
            System.out.println("running ...");
        }, initialDelay, period, TimeUnit.MICROSECONDS);


    }
}
