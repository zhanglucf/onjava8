package com.example.onjava8.chapter11;

// innerclasses/MultiImplementation.java
// For concrete or abstract classes, inner classes
// produce "multiple implementation inheritance"
// {java innerclasses.MultiImplementation}
//如果拥有的是抽象的类或具体的类，而不是接口，那就只能使用内部类才能实现多重继承：
class D {}

abstract class E {}

class Z extends D {
    E makeE() {
        return new E() {};
    }
}

public class MultiImplementation {
    static void takesD(D d) {}
    static void takesE(E e) {}

    public static void main(String[] args) {
        Z z = new Z();
        takesD(z);
        takesE(z.makeE());
    }
}

