package netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用NIO copy文件
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("nio.txt");
        FileOutputStream fos = new FileOutputStream("nio-copy.txt");
        //输入流获取channel
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        //创建缓冲
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        //将缓冲区的数据写入到channel
        while (true) {
            //limit和position的值如果相等，则read=0
            //limit和position的值如果相等其实也意味着buffer不可用了
            //position 会变化 向前移动 具体移动多少呢？ = read
            int read = inChannel.read(byteBuffer);//---【1】
            if (read == -1) {
                break;
            } else {
                /**
                 * limit = position
                 * position = 0
                 */
                byteBuffer.flip();
                //writer 之后 position 会往前移动【1】处的read的长度
                outChannel.write(byteBuffer);
                //使position复位（回到0位置）
                // 如果不clear 第一次读到buffer中的数据，会一直重复读取到
                /**
                 *position = 0
                 *limit = capacity
                 * buffer 恢复到 刚创建完成时的状态
                 */
                byteBuffer.clear();
            }
        }

        //关闭Channel
        inChannel.close();
        outChannel.close();
    }
}
