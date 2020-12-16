package io.basic;

import util.Timer;

import java.io.*;
import java.util.Arrays;

/**
 * 复制图片
 */
public class IO_9 {
    public static void main(String[] args) throws IOException {
        String str = "中国";
        byte[] bytes = str.getBytes();
        //[-28, -72, -83, -27, -101, -67]
//        System.out.println(Arrays.toString(str.getBytes()));
        //[-28, -72, -83, -27, -101, -67]
//        System.out.println(Arrays.toString(str.getBytes("UTF-8")));
        //[-42, -48, -71, -6]
//        System.out.println(Arrays.toString(str.getBytes("GBK")));

        byte[] bys = {-28, -72, -83, -27, -101, -67};
        byte[] bys2 = {-42, -48, -71, -6};

        System.out.println(new String(bys));
//        System.out.println(new String(bys,"GBK"));
        System.out.println(new String(bys,"UTF-8"));
        System.out.println("********************");
//        System.out.println(new String(bys2));
        System.out.println(new String(bys2,"GBK"));
//        System.out.println(new String(bys2,"UTF-8"));
    }
}
