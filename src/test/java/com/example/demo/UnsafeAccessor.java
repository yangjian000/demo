package com.example.demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeAccessor {

    private static final Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }

    }

    public static Unsafe getUnsafe() {
        return UNSAFE;
    }

}

class MyAtomicInteger {

    private volatile int value;
    private static final long valueOffset;
    private static final Unsafe UNSAFE;

    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    public void decrement(int amount) {
        while (true) {
            int pre = this.value;
            int next = pre - amount;
            if (UNSAFE.compareAndSwapInt(this, valueOffset, pre, next)) {
                break;
            }
        }
    }

}