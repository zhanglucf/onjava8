package io.channels;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author ZhangZhenhua
 * @date 2020/12/31 16:36
 */
public class GetChannel {

    /*
         旧式IO 类 FileInputStream、FileOutputStream，以及用于读写的 RandomAccessFile被FileChannel（文件通道取代）
         FileInputStream、FileOutputStream可以获取到FileChannel
         下面的栗子用来熟悉FileChannel的使用
     */
    private static final String path = "filechannel-a.txt";
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        /**
         * 通过FileOutputStream与RandomAccessFile的getChannel()方法获取到Channel对象有哪些区别？
         * 区别1：
         *  FileOutputStream对象获得的channel writer时是覆盖动作，即使指定了位置，仍然不生效 还是覆盖
         *  RandomAccessFile对象获得的channel writer时是替换动作，而且可以指定替换开始的位置
         * 区别2：
         *  FileOutputStream对象获得的channel没法通过size()得到文件的大小
         *  RandomAccessFile对象获得的channel没法通过size()得到文件的大小
         */


        //写入文件（覆盖）
        try (
                FileChannel channel = new FileOutputStream(path).getChannel()

        ) {
            ByteBuffer byteBuffer = ByteBuffer.wrap("美".getBytes(StandardCharsets.UTF_8));
            int write = channel.write(byteBuffer, 6);
//            System.out.println("写入".concat(write + "").concat("字节"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //向文件尾部追加
        try (
                FileChannel channel = new RandomAccessFile(path, "rw").getChannel()
        ) {
            ByteBuffer byteBuffer = ByteBuffer.wrap("\r\n向文件尾部追加".getBytes(StandardCharsets.UTF_8));
            int write = channel.write(byteBuffer, channel.size());
//            System.out.println("写入".concat(write + "").concat("字节"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读取文件
        try (
                FileChannel channel = new RandomAccessFile(path, "rw").getChannel()
        ) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(BSIZE);
            int read = channel.read(byteBuffer);
            byteBuffer.flip();
//            while (byteBuffer.hasRemaining()){
//                System.out.write(byteBuffer.get());
                System.out.println(new String(byteBuffer.array()));
//                byteBuffer.clear();
//               channel.read(byteBuffer);
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.flush();
    }
}
