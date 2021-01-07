package concurrent.jvm.atomic;

import concurrent.Nap;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 15:10
 */
public class EvenProducer_2 extends IntGenerator_0 {
    private int currentEvenValue = 0;
    @Override
    public int next() {
        /**
         * 在 Java 中，自增不是原子性的操作。
         * 因此，如果不保护任务，即使单纯的自增也不是线程安全的。
         */
        ++currentEvenValue; // [1]
        new Nap(0.01);//make failure faster
        ++currentEvenValue;
        return currentEvenValue;
    }
    public static void main(String[] args) {
        EvenChecker_1.test(new EvenProducer_2());
    }
}


