package socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Reciver {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8888);
        byte[] bys = new byte[1024];
        DatagramPacket receiverPacket = new DatagramPacket(bys, bys.length);
        while (true) {
            datagramSocket.receive(receiverPacket);
            System.out.println(receiverPacket.getAddress() + " : " + receiverPacket.getPort() + " " + new String(receiverPacket.getData(), 0, receiverPacket.getLength()));
            //给sender 回应
            DatagramPacket sendPacket = new DatagramPacket("收到".getBytes(), 0, "收到".getBytes().length, receiverPacket.getAddress(), receiverPacket.getPort());
            datagramSocket.send(sendPacket);
        }
    }
}
