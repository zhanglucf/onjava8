package io.basic;

import util.Timer;

import java.io.*;

/**
 * 复制图片
 */
public class IO_8 {
    public static void main(String[] args) throws IOException {
        Timer timer = new Timer();
        BufferedInputStream bin = new BufferedInputStream(
                new FileInputStream("D:\\cf安装包\\CrossFire_Client_5.3.1.0.7z.001"));
        BufferedOutputStream bout = new BufferedOutputStream(
                new FileOutputStream("onjava8\\zip\\CrossFire_Client_5.3.1.0.7z.001"));
        int len;
        byte[] by = new byte[1024];
        while (((len = bin.read(by)) != -1)) {
            bout.write(by,0,len);
        }
        bout.close();
        bin.close();
        System.out.println(timer.duration());
    }
}
