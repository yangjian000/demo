package com.example.demo.algorithm.test;

public class TestFactorial {
    public static void main(String[] args) {
        System.out.println(factorial(50));
    }

    private static long factorial(long n) {
        if (n == 1) {
            return 1;
        }
        return n * (factorial(n - 1));
    }
}
