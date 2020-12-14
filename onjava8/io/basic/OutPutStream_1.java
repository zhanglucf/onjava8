package io.basic;

import java.io.*;

public class OutPutStream_1 {
    public static void main(String[] args) {
        OutputStream out = null;
        try {
            out = new FileOutputStream("123.txt");
            out.write("123".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
