package concurrent.jvm.atomic;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 16:37
 */
public class SynchronizedSerialNumbers_11 extends SerialNumbers_9 {
    private int serialNumber = 0;

    //volatile 不再是必需的，因为 synchronized 关键字保证了 volatile （易变性） 的特性。
    public synchronized int nextSerialNumber() {
        return serialNumber++;
    }

    //    public int nextSerialNumber() {
    //not thread-self
//        return serialNumber = serialNumber+1;
//    }
    public static void main(String[] args) {
        SerialNumberChecker_10.test(new SynchronizedSerialNumbers_11());
    }
}
