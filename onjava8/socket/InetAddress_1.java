package socket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author ZhangZhenhua
 * @date 2020/12/18 12:14
 */
public class InetAddress_1 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress[] inetAddresses = InetAddress.getAllByName("DESKTOP-58H3EH6");
        for (InetAddress ia : inetAddresses) {
            System.out.format("getHostAddress:%s%n", ia.getHostAddress());
        }
    }
}
