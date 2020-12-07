package com.example.onjava8.chapter19;

// typeinfo/ClassInitialization.java
import java.util.*;

class Initable {
    /**
     * static final 值是“编译期常量”,
     * 这个值不需要对 Initable 类进行初始化就可以被读取
     */
    static final int STATIC_FINAL = 47;
    /**
     * 如果只是将一个字段设置成为 static 和 final，还不足以确保这种行为。
     * 例如，对 Initable.staticFinal2 的访问将强制进行类的初始化，因为它不是一个编译期常量。
     */
    static final int STATIC_FINAL2 =
            ClassInitialization.rand.nextInt(1000);
    static {
        System.out.println("Initializing Initable");
    }
}

class Initable2 {
    /**
     * 如果一个 static 字段不是 final 的，那么在对它访问时，
     * 总是要求在它被读取之前，要先进行链接（为这个字段分配存储空间）和初始化（初始化该存储空间）
     */
    static int staticNonFinal = 147;
    static {
        System.out.println("Initializing Initable2");
    }
}

class Initable3 {
    static int staticNonFinal = 74;
    static {
        System.out.println("Initializing Initable3");
    }
}

public class ClassInitialization {
    public static Random rand = new Random(47);
    public static void main(String[] args) throws Exception {
        Class initable = Initable.class;
        System.out.println("After creating Initable ref");
        // Does not trigger initialization:
        System.out.println(Initable.STATIC_FINAL);
        // Does trigger initialization:
        System.out.println(Initable.STATIC_FINAL2);
        // Does trigger initialization:
        System.out.println(Initable2.staticNonFinal);
        Class initable3 = Class.forName("com.example.onjava8.chapter19.Initable3");
        System.out.println("After creating Initable3 ref");
        System.out.println(Initable3.staticNonFinal);
    }
}

