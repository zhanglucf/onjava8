package concurrent;

import java.util.Random;

public class Breakable {
    private static final Random r = new Random(47);
    String id;
    private int failcount;

    public Breakable() {
        int i = r.nextInt(3);
        this.id = i + "@";
        this.failcount = i;

    }

    public Breakable(String id, int failcount) {
        this.id = id;
        this.failcount = failcount;
    }

    @Override
    public String toString() {
        return "Breakable_" + id + " [" + failcount + "]";
    }

    public static Breakable work(Breakable b) {
        if (--b.failcount == 0) {
            System.out.println(
                    "Throwing Exception for " + b.id + ""
            );
            throw new RuntimeException(
                    "Breakable_" + b.id + " failed"
            );
        }
        System.out.println(b);
        return b;
    }

    public static Breakable f(Breakable b) {
        System.out.println(b);
        return b;
    }
}
