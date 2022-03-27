package com.example.demo;


import java.util.Arrays;

public class SortAlgoTest {

    public static void main(String[] args) {

        int area = 200000;
        int[] arr = new int[area];

        for (int i = 0; i < area; i++) {
            arr[i] = (int) (Math.random() * 1000000);
        }
        Sort sort = new SelectSort();

        long start = System.currentTimeMillis();
        sort.sort(arr);
        long spends = System.currentTimeMillis() - start;
        System.out.println(spends);


//        new SelectSort().sort(arr);

//        System.out.println(Arrays.toString(arr));

    }

}


interface Sort {
    void sort(int[] arrays);
}

/**
 * 冒泡排序
 * 通过对待排序序列从前向后（从下标较小的元素开始），依次比较相邻元素的值，若发现逆序则交换，
 * 使值较大的元素逐渐从前移向后部，就像水底气泡一样逐渐向上冒
 */
class BubbleSort implements Sort {
    @Override
    public void sort(int[] arrays) {
        for (int i = 0; i < arrays.length - 1; i++) {
            boolean flag = false;
            for (int j = 0; j < arrays.length - 1 - i; j++) {
                if (arrays[j] > arrays[j + 1]) {
                    flag = true;
                    int temp = arrays[j];
                    arrays[j] = arrays[j + 1];
                    arrays[j + 1] = temp;
                }
            }
            if (!flag) {
                break;
            }
        }
    }
}


/**
 * 选择排序
 * 也属于内部排序，是从欲排序的数据中，按指定的规则选出某一元素，再依规定交换位置后达到排序的目的
 */
class SelectSort implements Sort {

    @Override
    public void sort(int[] arrays) {

        for (int i = 0; i < arrays.length - 1; i++) {
            int minIndex = i;
            int min = arrays[i];
            for (int j = i + 1; j < arrays.length; j++) {
                if (min > arrays[j]) {
                    minIndex = j;
                    min = arrays[j];
                }
                if (minIndex != i) {
                    arrays[minIndex] = arrays[i];
                    arrays[i] = min;
                }
            }
        }
    }
}