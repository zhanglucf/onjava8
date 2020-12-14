package io.basic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OutPutStream_2 {
    public static void main(String[] args) {
        try (
                OutputStream out = new FileOutputStream("123.txt");
        ) {
            out.write("123".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
