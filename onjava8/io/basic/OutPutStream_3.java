package io.basic;

import java.io.*;
import java.util.Arrays;

public class OutPutStream_3 {
    public static void main(String[] args) throws IOException {
        InputStream in = new FileInputStream("123.txt");
        byte[] by = new byte[3];//缓冲 小推车  从头装，如果小推车里已经有了货物，货物从前往后被替换掉

                                /*
                                    我们可以用高铁一等坐二等座三等座做类比，假定乘客都喜欢坐一等座，其次是二等座，最后才考虑三等座
                                    高铁一共可以载客3人，座位没有新的乘客使用，老乘客可以继续乘坐
                                    A B C \r \n
                                    E F G
                                    一共8名乘客，需要乘坐高铁
                                    首先来了一辆空车，A B C 分别坐 一等座 二等座 三等座
                                    下一站，\r \n E 分别乘坐一等坐二等座三等 ABC下车
                                    在下一站 F G 上车 \r \n下车，由于 E 的三等坐没人要做，E继续乘坐
                                    在下一站，犹豫没有人上车了，F G E 继续乘坐高铁
                                    在下一站，犹豫没有人上车了，F G E 继续乘坐高铁
                                    在下一站，犹豫没有人上车了，F G E 继续乘坐高铁

                                    8名乘客是我们要读取的数据的字节长度 \r\n代表换行
                                 */

        int read = in.read(by);
        System.out.print(new String(by));//12

        int read2 = in.read(by);
        System.out.print(new String(by));//3
        //
        int read3 = in.read(by);
        System.out.print(new String(by));//ab

        int read4 = in.read(by);
        System.out.print(new String(by));//c

        int read5 = in.read(by);
        System.out.print(new String(by));//c

        int read6 = in.read(by);
        System.out.print(new String(by));//c

        int read7 = in.read(by);
        System.out.println(new String(by));//c

        System.out.println("-----------");
        System.out.println(read);
        System.out.println(read2);
        System.out.println(read3);
        System.out.println(read4);
        System.out.println(read5);
        System.out.println(read6);
        System.out.println(read7);
        in.close();
    }
}
