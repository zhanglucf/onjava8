package concurrent.jvm.atomic;

import concurrent.Nap;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 15:10
 */
public class AtomicEvenProducer_13 extends IntGenerator_0 {
    private AtomicInteger currentEvenValue = new AtomicInteger(0);
    @Override
    public synchronized int next() {
       return currentEvenValue.addAndGet(2);
    }
    public static void main(String[] args) {
        EvenChecker_1.test(new AtomicEvenProducer_13());
    }
}


