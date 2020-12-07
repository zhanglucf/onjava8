package com.example.onjava8.chapter21;

import java.util.EnumSet;

public enum Color {
    红,橙,黄,绿,青,蓝,紫
}

class EnumSetTest{
    public static void main(String[] args) {
        EnumSet<Color> colors = EnumSet.allOf(Color.class);
        System.out.println(colors);
        EnumSet<Color> clone = colors.clone();
        System.out.println(clone);
        colors.clear();
        System.out.println(colors);
        colors.addAll(EnumSet.range(Color.青,Color.紫));
        System.out.println(colors);
        clone.remove(Color.红);
        System.out.println(clone);
        System.out.println(clone.retainAll(EnumSet.of(Color.黄, Color.紫)));
        System.out.println(clone);
        //补集
        System.out.println(EnumSet.complementOf(clone));
    }
}