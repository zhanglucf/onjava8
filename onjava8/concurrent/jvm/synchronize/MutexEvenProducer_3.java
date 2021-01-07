package concurrent.jvm.synchronize;


import concurrent.Nap;
import concurrent.jvm.atomic.EvenChecker_1;
import concurrent.jvm.atomic.IntGenerator_0;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenProducer_3 extends IntGenerator_0 {
    private int currentEvenValue = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public int next() {
        lock.lock();
        try {
            ++currentEvenValue;
            new Nap(0.01); // Cause failure faster
            ++currentEvenValue;
            return currentEvenValue;
        } finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        EvenChecker_1.test(new MutexEvenProducer_3());
    }
}
/*
No odd numbers discovered
*/
