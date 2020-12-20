package io.basic;

import java.io.*;

/**
 * 复制图片
 */
public class IO_16 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("io_10.txt"));

        String s ;
        while ((s = reader.readLine())!= null){
            System.out.print(s);
        }
        System.exit(1);
    }
}
