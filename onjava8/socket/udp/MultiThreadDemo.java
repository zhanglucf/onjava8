package socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class MultiThreadDemo {

    public static void main(String[] args) {
        new Thread(()->MultiThreadDemo.receiver()).start();
        new Thread(()->MultiThreadDemo.sender()).start();
    }

    public static void sender(){
        System.out.println("sender start");
        try {
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
                System.out.println(receiverPacket.getAddress() + ":" + receiverPacket.getPort() + " " + new String(receiverPacket.getData(), 0, receiverPacket.getLength()));
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiver() {
        System.out.println("receiver start");
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}