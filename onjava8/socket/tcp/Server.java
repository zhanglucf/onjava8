package socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();
        while (true) {
            InputStream is = socket.getInputStream();
            byte[] bys = new byte[1024];
            int len;
            while ((len = is.read(bys)) != -1) {
                System.out.println(new String(bys, 0, len));
            }
        }
    }
}
