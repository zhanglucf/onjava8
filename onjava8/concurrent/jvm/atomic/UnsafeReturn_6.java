package concurrent.jvm.atomic;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 16:12
 */
public class UnsafeReturn_6 extends IntTestable_4 {
    private int i = 0;
    public int getAsInt() { return i; }
    public synchronized void evenIncrement() {
        i++;
        i++;
    }
    public static void main(String[] args) {
        Atomicity_6.test(new UnsafeReturn_6());
    }
}
