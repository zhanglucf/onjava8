package chapter9;

// arrays/StreamFromArray.java

import java.util.*;

import static chapter9.ArrayShow.show;

public class StreamFromArray {
    public static void main(String[] args) {
        String[] s = new Rand.String().array(10);
        show(s);
        Arrays.stream(s).skip(3).limit(5).map(ss -> ss + "!").forEach(System.out::println);
        show(s);
        int[] ia = new Rand.Pint().array(10);
        Arrays.stream(ia).skip(3).limit(5)
                .map(i -> i * 10).forEach(System.out::println);
        Arrays.stream(new long[10]);
        Arrays.stream(new double[10]);
        // Only int, long and double work:
        // - Arrays.stream(new boolean[10]);
//         - Arrays.stream(new byte[10]);
        // - Arrays.stream(new char[10]);
        // - Arrays.stream(new short[10]);
        // - Arrays.stream(new float[10]);
        // For the other types you must use wrapped arrays:
        //只有“原生类型” int、long 和 double 可以与 Arrays.stream() 一起使用;
        // 对于其他的，您必须以某种方式获得一个包装类型的数组。
        float[] fa = new Rand.Pfloat().array(10);
        Arrays.stream(ConvertTo.boxed(fa));
        Arrays.stream(new Rand.Float().array(10));
    }
}


