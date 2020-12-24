package socket.udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Sender {
    public static void main(String[] args) throws IOException {
        InetAddress remoteAddress = InetAddress.getByName("192.168.1.3");
        int remotePort = 8888;
        DatagramSocket socket = new DatagramSocket(8889);
        Scanner scanner = new Scanner(System.in);
        byte[] bys = new byte[1024];
        DatagramPacket receiverPacket = new DatagramPacket(bys, bys.length);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("886")) {
                break;
            }
            DatagramPacket packet = new DatagramPacket(input.getBytes(), input.getBytes().length, remoteAddress, remotePort);
            socket.send(packet);
            //准备接收远端传来的数据
            socket.receive(receiverPacket);
            System.out.println(receiverPacket.getAddress() + ":" + receiverPacket.getPort() + " " + new String(receiverPacket.getData(),0,receiverPacket.getLength()));
        }
        socket.close();
    }
}
