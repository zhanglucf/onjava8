package io.basic;

import util.Timer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 复制图片
 */
public class IO_6 {
    public static void main(String[] args) throws IOException {
        Timer timer = new Timer();
        FileInputStream in = new FileInputStream("yage.jpg");
        OutputStream out = new FileOutputStream("onjava8/picture/yage.jpg");
        int len;
        byte[] by = new byte[1024];
        while (((len = in.read(by)) != -1)) {
            out.write(by,0,len);
        }
        out.close();
        in.close();
        System.out.println(timer.duration());
    }
}
