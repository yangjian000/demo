package com.example.demo.algorithm.sort;

import java.util.Arrays;

public class SortTest {
    public static void main(String[] args) {
        int[] a = {14, 18, 88, 24, 45};
        int[] sort = MergeSort.sort(a);
        System.out.println(Arrays.toString(sort));
    }
}

class MergeSort {

    public static int[] sort(int[] a) {
        return sort(a, 0, a.length-1);
    }

    private static int[] sort(int[] a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            sort(a, low, mid);
            sort(a, mid + 1, high);
            merge(a, low, mid, high);
        }
        return a;
    }

    private static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }

        if (i <= mid) {
            temp[k++] = a[i++];
        }

        if (j <= high) {
            temp[k++] = a[j++];
        }
        System.arraycopy(temp, 0, a, low, temp.length);
    }
}
