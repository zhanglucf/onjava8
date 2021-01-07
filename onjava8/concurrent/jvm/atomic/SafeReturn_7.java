package concurrent.jvm.atomic;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 16:23
 */
public class SafeReturn_7 extends IntTestable_4 {
    private int i = 0;
    public synchronized int getAsInt() { return i; }
    public synchronized void evenIncrement() {
        i++; i++;
    }
    public static void main(String[] args) {
        Atomicity_6.test(new SafeReturn_7());
    }
}
