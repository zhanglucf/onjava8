package chapter13;

/**
 * 连续自然数求和
 */
public class SumFunction {
    static IntCall fact;

    public static void main(String[] args) {
        fact = n -> n == 0 ? 0 :
                    n == 1 ? 1 :
                    n + fact.call(n - 1);
        System.out.println(fact.call(4));
    }
}
