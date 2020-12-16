package io.basic;

import util.Timer;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

/**
 * 复制图片
 */
public class IO_5 {

    public static void main(String[] args) throws IOException {
        Timer timer = new Timer();
        FileInputStream in = new FileInputStream("yage.jpg");
        OutputStream out = new FileOutputStream("onjava8/picture/yage.jpg");
        int i;
        while (((i = in.read()) != -1)) {
            out.write(i);
        }
        out.close();
        in.close();
        System.out.println(timer.duration());
    }
}
