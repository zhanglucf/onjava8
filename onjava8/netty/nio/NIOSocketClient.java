package netty.nio;

import concurrent.Nap;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author ZhangZhenhua
 * @date 2020/12/28 9:17
 */
public class NIOSocketClient {
    public static void main(String[] args) throws IOException {
        InetSocketAddress remoteAddress = new InetSocketAddress(InetAddress.getByName("192.168.100.33"), 8888);
        // SocketChannel.open(remoteAddress); 如果使用了带remoteAddress的方法，
        //则socketChannel.connect(remoteAddress)不需要再次调用
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        if (!socketChannel.connect(remoteAddress)) {
            //finishConnect()必须调用  才能connect成功
            while (!socketChannel.finishConnect()) {
                System.out.println("connecting server ...waiting");
            }
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            ByteBuffer buffer = ByteBuffer.wrap(scanner.nextLine().getBytes());
            socketChannel.write(buffer);
        }
    }
}
