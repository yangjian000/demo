package com.example.demo;

import java.io.Serializable;

public final class Singleton implements Serializable {

    private Singleton() {
    }

    private static final Singleton INSTANCE = new Singleton();

    public static Singleton getInstance() {
        return INSTANCE;
    }

}

enum Singletons {
    INSTANCE;
}

final class Singleton1 {

    private Singleton1() {
    }

    private volatile static Singleton1 INSTANCE = null;

    public static Singleton1 getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (Singleton1.class) {
            if (INSTANCE != null) {
                return INSTANCE;
            }
            INSTANCE = new Singleton1();
            return INSTANCE;
        }
    }
}

final class Singleton2 {
    private Singleton2() {
    }

    private static Singleton2 INSTANCE = new Singleton2();

    public static Singleton2 getInstance() {
        return INSTANCE;
    }
}


final class Singleton3 {
    private Singleton3() {
    }

    private static class LazyHolder {
        final static Singleton3 INSTANCE = new Singleton3();
    }

    public static Singleton3 getInstance() {
        return LazyHolder.INSTANCE;
    }


}