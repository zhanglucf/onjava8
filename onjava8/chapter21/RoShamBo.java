package chapter21;

// enums/RoShamBo.java
// Common tools for RoShamBo examples
public class RoShamBo {
    public static <T extends Competitor<T>> void match(T a, T b) {
        System.out.println(a + " vs. " + b + ": " + a.compete(b));
    }

    /**
     * 类型参数必须同时是 Enum<T> 类型 和 Competitor<T> 类型
     * Enum<T> 类型:它将在 Enums.random() 中使用
     * Competitor<T> 类型:它将被传递给 match() 方法）
     */
    public static <T extends Enum<T> & Competitor<T>> void play(Class<T> rsbClass, int size) {
        for (int i = 0; i < size; i++) {
            match(Enums.random(rsbClass), Enums.random(rsbClass));
        }
    }
}

