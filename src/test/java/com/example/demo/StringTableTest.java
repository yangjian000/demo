package com.example.demo;


/**
 * -Xmx10 -XX:+PrintStringTablesStatistics -XX:+PrintGCDetails -verbose:gc
 */
public class StringTableTest {

    public static void main(String[] args) {
        int i = 0;
        try {
            for (int j = 0; j < 10000; j++, i++) {
                String.valueOf(j).intern();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(i);
        }
    }
}
