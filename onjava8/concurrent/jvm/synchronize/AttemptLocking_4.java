package concurrent.jvm.synchronize;// lowlevel/AttemptLocking.java
// Locks in the concurrent library allow you
// to give up on trying to acquire a lock
import concurrent.Nap;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class AttemptLocking_4 {
    private ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            System.out.println("tryLock(): " + captured);
        } finally {
            if(captured)
                lock.unlock();
        }
    }
    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
        } finally {
            if(captured)
                lock.unlock();
        }
    }

    public static void main(String[] args) {
        final AttemptLocking_4 al = new AttemptLocking_4();
        al.untimed(); // True -- lock is available
        al.timed();   // True -- lock is available
        // Now create a second task to grab the lock:
        CompletableFuture.runAsync( () -> {
            al.lock.lock();
            System.out.println("acquired");
        });
        new Nap(0.1);  // Give the second task a chance
        al.untimed(); // False -- lock grabbed by task
        al.timed();   // False -- lock grabbed by task
    }
}

