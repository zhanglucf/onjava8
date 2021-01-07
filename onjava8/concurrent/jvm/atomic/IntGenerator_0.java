package concurrent.jvm.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 14:51
 */
public abstract class IntGenerator_0 {

    private AtomicBoolean canceled = new AtomicBoolean();

    public abstract int next();

    public void cancel() { canceled.set(true); }

    public boolean isCanceled() {
        return canceled.get();
    }
}
