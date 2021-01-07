package concurrent.jvm.atomic;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 16:38
 */
public class SerialNumbers_9 {
    private volatile int serialNumber = 0;
    public int nextSerialNumber() {
        return serialNumber++; // Not thread-safe
    }
}
