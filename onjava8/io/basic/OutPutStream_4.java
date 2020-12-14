package io.basic;

import java.io.*;

public class OutPutStream_4 {
    public static void main(String[] args) throws IOException {
        InputStream in = new FileInputStream("123.txt");
        OutputStream out = new FileOutputStream("321.txt");
        byte[] by = new byte[3];
        int len ;
        while ((len = in.read(by)) != -1){
            out.write(by,0,len);
        }
        System.out.println("-----------");
        in.close();
    }
}
