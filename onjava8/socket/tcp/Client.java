package socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class  Client {
    public static void main(String[] args) throws IOException {
        System.out.println("client 启动");

        //server端的ip
//        String remoteHost = "192.168.1.3";
        String remoteHost = "192.168.100.33";
        //server端的port
        Integer remotePort = 8888;
        Socket socket = new Socket(remoteHost, remotePort);
        Scanner scanner = new Scanner(System.in);
        OutputStream outputStream = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        byte[] bys = new byte[1024];
        int len;
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("")) {
                continue;
            }
            outputStream.write(input.getBytes());
            len = is.read(bys);
            System.out.println(socket.getPort() + " : " + new String(bys,0,len));
            if (input.equals("886")) {
                break;
            }
        }
        System.out.println("client 关闭");
        socket.close();
    }
}
