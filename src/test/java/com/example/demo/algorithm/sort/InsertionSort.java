package com.example.demo.algorithm.sort;

public class InsertionSort {

    private static boolean greater(Comparable a, Comparable b) {
        return a.compareTo(b) > 0;
    }

    private static  void exchange(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
