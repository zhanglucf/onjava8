package netty.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用NIO写文件
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "hello file channel";
        FileOutputStream fos = new FileOutputStream("NIOFileChannel01.txt");
        //输出流获取channel
        FileChannel channel = fos.getChannel();
        //创建缓冲
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将数据写入buffer
        byteBuffer.put(str.getBytes());
        //反转
        byteBuffer.flip();
        //将缓冲区的数据写入到channel
        channel.write(byteBuffer);
        //关闭流
        channel.close();
    }
}
