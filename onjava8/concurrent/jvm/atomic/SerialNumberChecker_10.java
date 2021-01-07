package concurrent.jvm.atomic;

import concurrent.Nap;

import java.util.concurrent.CompletableFuture;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 16:36
 */
public class SerialNumberChecker_10 implements Runnable {
    private CircularSet_8 serials = new CircularSet_8(1000);
    private SerialNumbers_9 producer;

    public SerialNumberChecker_10(SerialNumbers_9 producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        while (true) {
            int serial = producer.nextSerialNumber();
            if (serials.contains(serial)) {
                System.out.println("Duplicate: " + serial);
                System.exit(0);
            }
            serials.add(serial);
        }
    }

    static void test(SerialNumbers_9 producer) {
        for (int i = 0; i < 10; i++) {
            CompletableFuture.runAsync(new SerialNumberChecker_10(producer));
        }
        new Nap(600, "No duplicates detected");
    }
}
