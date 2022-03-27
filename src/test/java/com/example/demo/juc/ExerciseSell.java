package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

@Slf4j
public class ExerciseSell {

    public static void main(String[] args) throws InterruptedException {
        TicketWindow window = new TicketWindow(1000);

        List<Thread> threadList = new ArrayList<>();
        List<Integer> list = new Vector<>();
        for (int i = 0; i < 4000; i++) {
            Thread thread = new Thread(() -> {
                int amount = window.sell(randomAmount());
                try {
                    Thread.sleep(randomAmount());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(amount);
            });
            threadList.add(thread);
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }

        log.debug("余票:{}", window.getCount());
        log.debug("卖出票数：{}", list.stream().mapToInt(i -> i).sum());
    }

    static Random random = new Random();

    private static int randomAmount() {
        return random.nextInt(5) + 1;
    }
}

class TicketWindow {
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int sell(int amount) {
        if (this.count >= amount) {
            this.count -= amount;
            return amount;
        } else {
            return 0;
        }
    }
}
