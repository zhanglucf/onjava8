package socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        //指定server socket的port
        int port = 8888;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        while (true) {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            byte[] bys = new byte[1024];
            int len;
            //read 阻塞
            while ((len = is.read(bys)) != -1) {
                System.out.println(socket.getPort() + " : " + new String(bys, 0, len));
                os.write("数据已经收到".getBytes());
            }
        }
    }
}
