package netty.nio;

import concurrent.Nap;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author ZhangZhenhua
 * @date 2020/12/28 11:14
 */
public class NIOChatRoomClient {
     Selector selector = null;
    static String username = null;

    public static void main(String[] args) throws IOException {
        NIOChatRoomClient nioChatRoomClient = new NIOChatRoomClient();
        InetSocketAddress remoteAddress = new InetSocketAddress(InetAddress.getByName("192.168.100.33"), 8888);
        // SocketChannel.open(remoteAddress); 如果使用了带remoteAddress的方法，
        //则socketChannel.connect(remoteAddress)不需要再次调用
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        nioChatRoomClient.selector = Selector.open();
        socketChannel.register(nioChatRoomClient.selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
        if (!socketChannel.connect(remoteAddress)) {
            //finishConnect()必须调用  才能connect成功
            while (!socketChannel.finishConnect()) {
                System.out.println("connecting server ...waiting");
            }
        }
        username = socketChannel.getLocalAddress().toString().substring(1);
        new Thread(nioChatRoomClient::receiveMsg).start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            ByteBuffer buffer = ByteBuffer.wrap((username + "说:" + scanner.nextLine()).getBytes());
            socketChannel.write(buffer);
        }
    }

    private void receiveMsg() {
        while (true) {
            new Nap(100);
            try {
                if (selector.select() > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        try {
                            channel.read(buffer);
                        } catch (IOException e) {
                            key.cancel();
                        }
                        System.out.println(new String(buffer.array()));
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
