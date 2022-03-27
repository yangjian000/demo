package com.example.demo;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        System.out.println(unsafe);

        long idOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("name"));

        Student s = new Student();
        unsafe.compareAndSwapInt(s,idOffset,0,1);
        unsafe.compareAndSwapObject(s,nameOffset,null,"lisi");

        System.out.println(s);

    }
}

@Data
class Student {
    volatile int id;
    volatile String name;
}