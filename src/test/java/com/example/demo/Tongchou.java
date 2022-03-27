package com.example.demo;


public class Tongchou {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("washing ...");
            sleep(1);
            System.out.println("born water...");
            sleep(5);
        }, "t1");

        Thread t2 = new Thread(()->{
            System.out.println("wash cup");
            sleep(1);
            System.out.println("wash tea cup");
            sleep(1);
            System.out.println("take tea");
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("drink tea ...");
        },"t2");

        t1.start();
        t2.start();
    }

    private static void sleep(int time){
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
