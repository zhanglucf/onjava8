package com.example.onjava8.chapter8;

// generics/GenericMethods.java

public class GenericMethods {
    public <T> void f(T x) {
        System.out.println(x.getClass().getName());
    }
    public <U> U g(U x) {
        System.out.println(x.getClass().getName());
        return x;
    }
    public static void main(String[] args) {
        GenericMethods gm = new GenericMethods();
        gm.f("");
        gm.f(1);
        gm.f(1.0);
        gm.f(1.0F);
        gm.f('c');
        gm.f(gm);
        System.out.println("-------------");
        String g = gm.g("");
        Integer g1 = gm.g(1);
        Double g2 = gm.g(1.0);
        Float g3 = gm.g(1.0f);
        Character c = gm.g('c');
        GenericMethods g4 = gm.g(gm);
    }
}

