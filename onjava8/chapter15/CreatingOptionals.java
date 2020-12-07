package com.example.onjava8.chapter15;

import java.util.Optional;

/**
 * 当我们在自己的代码中加入 Optional 时，可以使用下面 3 个静态方法：
 *
 * empty()：生成一个空 Optional。
 * of(value)：将一个非空值包装到 Optional 里。
 * ofNullable(value)：针对一个可能为空的值，为空时自动生成 Optional.empty，否则将值包装在 Optional 中。
 */
public class CreatingOptionals {
    public static void main(String[] args) {
        System.out.println("emptyOP");
        Optional<Object> emptyOP = Optional.empty();
        System.out.println(emptyOP.orElse("null"));

        System.out.println("of");
        Optional<String> ofOP = Optional.of("hello java");
        try {
            Optional<String> ofOP2 = Optional.of(null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        System.out.println(ofOP.orElse("null"));


        System.out.println("ofNullable");
        Optional<String> ofNullableOP = Optional.ofNullable("hello java");
        Optional<String> ofNullableOP2 = Optional.ofNullable(null);
        System.out.println(ofNullableOP.orElse("null"));
        System.out.println(ofNullableOP2.orElse("null"));

    }
}
