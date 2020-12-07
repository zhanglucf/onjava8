package com.example.onjava8.chapter6;

// strings/Replacing.java

public class Replacing {
    static String s = Splitting.knights;
    public static void main(String[] args) {
        //第一个表达式要匹配的是，以字母 f 开头，后面跟一个或多个字母（注意这里的 w 是小写的）
        System.out.println(s.replaceFirst("f\\w+", "located"));
        //要匹配的是三个单词中的任意一个，因为它们以竖线分割表示“或”，并且替换所有匹配的部分。
        System.out.println(s.replaceAll("shrubbery|tree|herring","banana"));
    }
}
