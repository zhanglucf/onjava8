package chapter9;

// arrays/ModifyExisting.java

import java.util.*;

import static chapter9.ArrayShow.show;

public class ModifyExisting {
    public static void main(String[] args) {
        double[] da = new double[7];
        Arrays.setAll(da, new Rand.Double()::get);
        show(da);
        Arrays.setAll(da, n -> da[n] / 100); // [1]
        show(da);

    }
}

