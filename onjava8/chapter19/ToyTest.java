package com.example.onjava8.chapter19;

// typeinfo/toys/ToyTest.java
// 测试 Class 类
// {java typeinfo.toys.ToyTest}

interface HasBatteries {}
interface Waterproof {}
interface Shoots {}

public class ToyTest {
    static void printInfo(Class cc) {
        System.out.println("Class name: " + cc.getName() +
                " is interface? [" + cc.isInterface() + "]");
        System.out.println(
                "Simple name: " + cc.getSimpleName());
        System.out.println(
                "Canonical name : " + cc.getCanonicalName());
    }

    public static void main(String[] args) {
        Class c = null;
        try {
            c = Class.forName("com.example.onjava8.chapter19.FancyToy");
        } catch(ClassNotFoundException e) {
            System.out.println("Can't find FancyToy");
            System.exit(1);
        }

        printInfo(c);
        for(Class face : c.getInterfaces())
            printInfo(face);

        Class up = c.getSuperclass();
        Object obj = null;

        try {
            // Requires no-arg constructor:
            obj = up.newInstance();
        } catch(InstantiationException e) {
            System.out.println("Cannot instantiate");
            System.exit(1);
        } catch(IllegalAccessException e) {
            System.out.println("Cannot access");
            System.exit(1);
        }

        printInfo(obj.getClass());
    }
}

