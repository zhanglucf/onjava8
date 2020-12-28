package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ZhangZhenhua
 * @date 2020/12/28 8:35
 */
public class NIOSocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));
        System.out.println("Server socket 启动");
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            while (selector.select(1000) == 0) {
            }
            //是selectedKeys() 不是keys() selector.selectedKeys需要我们主动清空
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("有新的client-" + selectionKey.hashCode() + "加入");
                }
                if (key.isReadable() && key.isValid()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    try {
//                        System.out.println("收到client-" + key.hashCode() + "的消息");
                        socketChannel.read(byteBuffer);
                        byteBuffer.clear();
                        System.out.println("client-" + key.hashCode() + ":"+ new String(byteBuffer.array()));
                    } catch (IOException e) {
                        System.out.println("client-" + key.hashCode() + " 关闭。。。");
                        key.cancel();
                    }
                }
                iterator.remove();
            }
        }
    }
}
