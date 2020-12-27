package socket.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) throws IOException {
        String remoteHost = "192.168.1.3";
        Integer remotePort = 8888;
        Socket socket = new Socket("192.168.1.3", 8888);
        Scanner scanner = new Scanner(System.in);
        OutputStream outputStream = socket.getOutputStream();
        while (true) {
            String input = scanner.nextLine();
            outputStream.write(input.getBytes());
            if (input.equals("886")) {
                break;
            }
        }
        socket.close();
    }
}
