package io.basic;

import java.io.*;

/**
 * 复制图片
 */
public class IO_17 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("io_15.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("io_17.txt"));

        String s;
        while ((s = reader.readLine())!=null){
            writer.write(s);
            writer.newLine();
            writer.flush();
        }
//        writer.flush();

        reader.close();
        writer.close();
        System.exit(1);
    }
}
