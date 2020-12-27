package netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用NIO读文件
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("NIOFileChannel01.txt");
        //输入流获取channel
        FileChannel channel = fis.getChannel();
        //创建缓冲
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将缓冲区的数据写入到channel
        channel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        //关闭流
        channel.close();
    }
}
