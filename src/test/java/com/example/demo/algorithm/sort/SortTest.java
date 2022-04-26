package com.example.demo.algorithm.sort;

import java.util.Arrays;

public class SortTest {
    public static void main(String[] args) {
        int[] a = {14, 18, 88, 24, 45};
//        int[] sort = MergeSort.sort(a);
//        System.out.println(Arrays.toString(sort));

        QuickSort.sort(a);
        System.out.println(Arrays.toString(a));

        whiles(10);
    }

    public static int whiles(int loops) {
        if (loops == 0) {
            System.out.println(loops);
            return loops;
        }
        System.out.println(loops);
        return whiles(loops - 1);
    }
}

class MergeSort {

    public static int[] sort(int[] a) {
        return sort(a, 0, a.length - 1);
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

class QuickSort {

    public static void main(String[] args) {
        int[] a = {1, 3, 2, 4, 5};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int low, int high) {
        if (low < high) {
            int i = low, j = high, temp = a[low];

            while (i < j) {
                while (i < j && a[j] >= temp) {
                    j--;
                }
                if (i < j) {
                    a[i++] = a[j];
                }

                while (i < j && a[i] < temp) {
                    i++;
                }

                if (i < j) {
                    a[j--] = a[i];
                }
            }
            a[i] = temp;
            sort(a, low, i - 1);
            sort(a, i + 1, high);
        }

    }
}

