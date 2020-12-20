package io.basic;

import java.io.*;

/**
 * 复制图片
 */
public class IO_14 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("io_10.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("io_14.txt"));

//        int len;
//        while ((len = reader.read()) != -1) {
//            writer.write(len);
//            writer.flush();
//        }

        char[] chs = new char[1024];
        int len;
        while ((len = reader.read(chs)) != -1) {
            writer.write(chs,0,len);
            writer.flush();
        }

        reader.close();
        writer.close();
        System.exit(1);
    }
}
