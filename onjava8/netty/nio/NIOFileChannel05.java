package netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用NIO的transferFrom() copy文件
 */
public class NIOFileChannel05 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("nio.txt", "rw");
        FileChannel channel = raf.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(1,(byte) 'B');
        raf.close();
    }
}
