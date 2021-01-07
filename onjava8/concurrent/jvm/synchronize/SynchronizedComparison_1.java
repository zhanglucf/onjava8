package concurrent.jvm.synchronize;

import concurrent.Nap;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 17:11
 */

abstract class Guarded {
    AtomicLong callCount = new AtomicLong();

    public abstract void method();

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + callCount.get();
    }
}

class SynchronizedMethod extends Guarded {
    public synchronized void method() {
        new Nap(1);
        callCount.incrementAndGet();
    }
}

class CriticalSection extends Guarded {
    public void method() {
        new Nap(1);
        synchronized (this) {
            callCount.incrementAndGet();
        }
    }
}

class Caller implements Runnable {
    private Guarded g;

    Caller(Guarded g) {
        this.g = g;
    }

    private AtomicLong successfulCalls = new AtomicLong();
    private AtomicBoolean stop = new AtomicBoolean(false);

    @Override
    public void run() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                stop.set(true);
            }
        }, 2500);

        while (!stop.get()) {
            g.method();
            successfulCalls.getAndIncrement();
        }
        System.out.println("-> " + successfulCalls.get());
    }
}

public class SynchronizedComparison_1 {
    static void test(Guarded g) {
        List<CompletableFuture<Void>> callers =
                Stream.of( new Caller(g),new Caller(g),new Caller(g),new Caller(g))
                        .map(CompletableFuture::runAsync)
                        .collect(Collectors.toList());
        callers.forEach(CompletableFuture::join);
        System.out.println(g);
    }

    public static void main(String[] args) {
        test(new CriticalSection());
        test(new SynchronizedMethod());
    }
}

