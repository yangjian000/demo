package com.example.demo;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;


/**
 *
 * 元空间溢出
 * -XX:MaxMetaspaceSize=8m
 * */
public class MemoryOverFlow extends ClassLoader {

    public static void main(String[] args) {
        int j = 0;

        try {
            MemoryOverFlow test = new MemoryOverFlow();
            for (int i = 0; i < 10000; i++, j++) {
                ClassWriter cw = new ClassWriter(0);
                cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Class" + i, null, "java/lang/Object", null);
                byte[] code = cw.toByteArray();
                test.defineClass("Class" + i, code, 0, code.length);
            }
        } finally {
            System.out.println(j);
        }
    }

}
