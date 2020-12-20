package io.itheima;

/**
 * 阶乘
 */
public class Recursion_4 {
    public static void main(String[] args) {
        System.out.println(jc(5));
//        System.out.println(jc_method_for(5));
    }

    /**
     * 递归实现
     * N! = N * (N-1)!
     * @param l
     * @return
     */
    static long jc(long l) {
        if (l == 1) {
            return 1;
        } else {
            return l * jc(l - 1);
        }
    }

    /**
     * 循环实现
     *  N! = N * (N-1)*(N-2)* ... * 1
     */
    static long jc_method_for(long l) {
        long r = 1L;
        for (int i = 1; i < l + 1; i++) {
            r *= i;
        }
        return r;
    }
}
