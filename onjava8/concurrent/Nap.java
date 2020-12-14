package concurrent;

import java.util.concurrent.TimeUnit;

public class Nap {

    public Nap(double t) {
        try {
            TimeUnit.MILLISECONDS.sleep((long)t );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Nap(double t,String msg) {
        try {
            TimeUnit.MILLISECONDS.sleep((long)t );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(msg);
    }
}
