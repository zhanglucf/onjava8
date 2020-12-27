package netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用NIO的transferFrom() copy文件
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("nio.txt");
        FileOutputStream fos = new FileOutputStream("nio-transferFrom.txt");
        //输入流获取channel
        FileChannel inChannel = fis.getChannel();
        System.out.println(inChannel.size());
        FileChannel outChannel = fos.getChannel();
        outChannel.transferFrom(inChannel,0,inChannel.size());
        //关闭Channel
        inChannel.close();
        outChannel.close();
    }
}
