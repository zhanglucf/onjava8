package io.basic;

import java.io.*;

/**
 * 复制图片
 */
public class IO_15 {
    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("io_15.txt"));

        for (int i = 0; i < 10; i++) {
            writer.write("hello world");
            writer.newLine();
            writer.flush();
        }
        writer.close();
        System.exit(1);
    }
}
