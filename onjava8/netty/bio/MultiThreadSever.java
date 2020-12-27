package netty.bio;

import util.Print;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.printlnTN;

/**
 * 思考： 为什么要在新的线程中执行socket的getInputStream()方法？
 * read()阻塞式接口，主线程会一直阻塞在【2】处的while循环里，无法
 * 再次执行到【1】处，导致server端无法处理其他client的消息
 *
 * tip:
 * windows 可以用telnet命令 来模拟客户端
 * 用法：
 *     步骤1 telnet 127.0.0.1 8888
 *     步骤2 Ctrl + ] (进入发送消息的模式)
 *     步骤3 sen msg （固定写法 eg: sen hello）
 *     步骤4 退出 q
 *
 */
public class MultiThreadSever {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        ExecutorService executor = Executors.newCachedThreadPool();
        while (true) {
            //accept 是阻塞接口
            printlnTN("服务端启动。。。");
            printlnTN("服务端等待客户端加入");
            Socket socket = server.accept();//----- 【1】
            executor.execute(() -> {
                printlnTN("有新的客户端加入");
                try {
                    //获取输入流 读取socket中的数据
                    printlnTN("读取客户端输入的数据");
                    InputStream inputStream = socket.getInputStream();
                    printlnTN("获取到客户端的输入流对象");
                    byte[] bys = new byte[1024];
                    int len;
                    //read 是一个阻塞式接口
                    while ((len = inputStream.read(bys)) != -1) {  //----- 【2】
                        printlnTN(new String(bys, 0, len));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
