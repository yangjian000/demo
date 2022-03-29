package com.example.demo.juc;

public class ABCProblem {
    public static void main(String[] args) {
        WaitNotify wn = new WaitNotify(1, 10);
        new Thread(() -> {
            wn.print("A", 1, 2);
        }).start();
        new Thread(() -> {
            wn.print("B", 2, 3);
        }).start();
        new Thread(() -> {
            wn.print("C", 3, 1);
        }).start();

    }
}

class WaitNotify {

    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (waitFlag != flag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }

    int flag;
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
}
