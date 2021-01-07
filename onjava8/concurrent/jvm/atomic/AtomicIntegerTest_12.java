package concurrent.jvm.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 16:52
 */
public class AtomicIntegerTest_12 extends SerialNumbers_9 {
    private AtomicInteger iserialNumber = new AtomicInteger(0);

    @Override
    public int nextSerialNumber() {
        return iserialNumber.addAndGet(1);
    }

    public static void main(String[] args) {
        SerialNumberChecker_10.test(new AtomicIntegerTest_12());
    }
}
