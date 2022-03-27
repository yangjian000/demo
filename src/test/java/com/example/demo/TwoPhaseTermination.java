package com.example.demo;

public class TwoPhaseTermination {

    private Thread monitor;

    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "Receive an interrupt require ...");
                    break;
                }
                try {
                    Thread.sleep(1_000);
                    System.out.println(Thread.currentThread().getName() + "execute monitor");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    current.interrupt();
                }
            }


        },"Monitor ");
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }
}

class Test {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();
        Thread.sleep(5_000);
        tpt.stop();
    }

}