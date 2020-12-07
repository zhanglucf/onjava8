package chapter5;

public class CountedInteger {
    private static long count;
    private final long id = count++;

    @Override
    public String toString() {
        return "CountedInteger:" + id;
    }
}

