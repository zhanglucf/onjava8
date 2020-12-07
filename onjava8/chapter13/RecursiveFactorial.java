package chapter13;

/**
 * 阶乘
 */
public class RecursiveFactorial {
    static IntCall fact;

    public static void main(String[] args) {
        System.out.println(fact);
        fact = n -> n == 0 ? 1 : n * fact.call(n - 1);
        System.out.println(fact.call(3));
    }
}
