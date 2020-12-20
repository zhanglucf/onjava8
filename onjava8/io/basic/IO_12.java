package io.basic;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 复制图片
 */
public class IO_12 {
    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader("io_10.txt");
        FileWriter writer = new FileWriter("io_12.txt");

        int i;
        while ((i = reader.read())!=-1){
            writer.write(i);
        }
//        writer.flush();

        reader.close();
        writer.close();
        System.exit(1);
    }
}
