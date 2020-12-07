package com.example.onjava8.chapter8;

// generics/GenericHolder.java

public class GenericHolder<T> {
    private T a;
    public GenericHolder() {}
    public void set(T a) { this.a = a; }
    public T get() { return a; }

    public static void main(String[] args) {
        GenericHolder<Automobile> h3 = new GenericHolder<Automobile>();
        h3.set(new Automobile()); // 此处有类型校验
        Automobile a = h3.get();  // 无需类型转换
        //- h3.set("Not an Automobile"); // 报错
        //- h3.set(1);  // 报错
    }
}

