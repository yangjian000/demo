package com.example.demo.nio.jdk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

// transfer底层使用零拷贝 效率高
public class FileChannelTransferTo {
    public static void main(String[] args) {

        try (FileChannel from = new FileInputStream("from.txt").getChannel(); FileChannel to = new FileOutputStream("to.txt").getChannel()) {

            long size = from.size();
            System.out.println(size);

            for (long left = size; left > 0; ) {
                System.out.println("position:"+(size-left)+" left:"+left);
                left -= from.transferTo((size - left), left, to);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
