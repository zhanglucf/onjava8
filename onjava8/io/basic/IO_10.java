package io.basic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 复制图片
 */
public class IO_10 {
    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("io_10.txt");
        /**
         *void write(int c)
         */
//        writer.write(97);
        /**
         * void write(char cbuf[])
         */
//        writer.write("中国".toCharArray());
        /**
         *
         */
//        writer.write("中国".toCharArray(),0,1);
        /**
         * void write(String str)
         */
//        writer.write("中国");
        /**
         * write(String str, int off, int len)
         */
        writer.write("雅阁、天籁、凯美瑞", 0, 2);

        writer.close();
    }
}
