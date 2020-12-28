package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ZhangZhenhua
 * @date 2020/12/28 11:14
 */
public class NIOChatRoomServer {
    private static Selector selector = null;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));
        System.out.println("Server socket 启动");
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            while (selector.select(1000) == 0) {
                System.out.println("wait...");
            }
            //是selectedKeys() 不是keys() selector.selectedKeys需要我们主动清空
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("有新的client-" + selectionKey.hashCode() + "加入");
                }
                if (key.isReadable()) {
                    handlerRead(key);
                }
                iterator.remove();
            }
        }
    }

    private static void handlerRead(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            socketChannel.read(buffer);
            String msg = new String(buffer.array());
            System.out.println("client-" + key.hashCode() + "@@@" + msg);
            sendMsgToOthers(msg, key);
        } catch (IOException e) {
            sendMsgToOthers("client-" + key.hashCode() + " 退出群聊", key);
            System.out.println("client-" + key.hashCode() + " 关闭。。。");
            key.cancel();
        }
    }

    private static void sendMsgToOthers(String msg, SelectionKey key) {
        System.out.println("转发消息");
        selector.keys().stream()
                .filter(k -> k != key)
                .filter(k -> k.channel() instanceof SocketChannel)
                .forEach(k -> {
                    SocketChannel channel = (SocketChannel) k.channel();
                    try {
                        channel.write(ByteBuffer.wrap(msg.getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
