package io.basic;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 复制图片
 */
public class IO_13 {
    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader("io_10.txt");
        FileWriter writer = new FileWriter("io_13.txt");
        
        char[] chs = new char[32];
        int len;
        while ((len = reader.read(chs))!=-1){
            writer.write(chs,0,len);
        }
//        writer.flush();

        reader.close();
        writer.close();
        System.exit(1);
    }
}
