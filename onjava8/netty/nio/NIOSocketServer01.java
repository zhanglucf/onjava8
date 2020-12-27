package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NIOSocketServer01 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress(8888);
        server.socket().bind(socketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(3);
        byteBuffers[1] = ByteBuffer.allocate(6);
        SocketChannel socketChannel = server.accept();
        while (true) {
            long read = socketChannel.read(byteBuffers);
            System.out.println("read total count=" + read);
            Arrays.stream(byteBuffers).forEach(x -> System.out.println("position=" + x.position() + " limit" + x.limit()));
            Arrays.stream(byteBuffers).forEach(ByteBuffer::flip);
            long write = socketChannel.write(byteBuffers);
            System.out.println("writer total count=" + write);
            Arrays.stream(byteBuffers).forEach(ByteBuffer::clear);
        }
    }
}
