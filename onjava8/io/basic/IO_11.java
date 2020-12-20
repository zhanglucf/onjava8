package io.basic;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 复制图片
 */
public class IO_11 {
    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader("io_10.txt");
        /**
         * 返回单个字符
         * int read()
         */
//        int i;
//        while ((i = reader.read())!=-1){
//            System.out.print((char)i);
//        }
//        System.out.println();

//        char[] chs = new char[2];
//        int len;
//        while ((len = reader.read(chs)) != -1) {
//            System.out.println(new String(chs,0,len));
//        }

        reader.close();
    }
}
