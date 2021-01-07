package concurrent.jvm.atomic;

import java.util.function.IntSupplier;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 16:08
 */
public abstract class IntTestable_4 implements Runnable, IntSupplier {
    abstract void evenIncrement();

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

}
