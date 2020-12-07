package com.example.onjava8.chapter5;

// typeinfo/FamilyVsExactType.java
// instanceof 与 class 的差别
// {java typeinfo.FamilyVsExactType}

class Base {
}

class Derived extends Base {
}

public class FamilyVsExactType {
    static void test(Object x) {
        System.out.println("Testing x of type " + x.getClass());
        String simpleName = x.getClass().getSimpleName();
        //x 是不是Base的子类或者和Base是同一类型 (你是这个类，还是从这个类派生的类)
        System.out.println(simpleName + " instanceof Base " + (x instanceof Base));
        System.out.println(simpleName + " instanceof Derived " + (x instanceof Derived));
        //x 是不是Base的子类或者和Base是同一类型
        System.out.println("Base.isInstance(" + simpleName + ") " + Base.class.isInstance(x));
        System.out.println("Derived.isInstance("+simpleName+") " + Derived.class.isInstance(x));
        System.out.println("Base.class.isAssignableFrom(" + simpleName + ") " + Base.class.isAssignableFrom(x.getClass()));
        System.out.println("Derived.class.isAssignableFrom("+simpleName+") " + Derived.class.isAssignableFrom(x.getClass()));
        System.out.println(simpleName + " .getClass() == Base.class " + (x.getClass() == Base.class));
        System.out.println(simpleName + " .getClass() == Derived.class " + (x.getClass() == Derived.class));
        System.out.println(simpleName + " .getClass().equals(Base.class)) " + (x.getClass().equals(Base.class)));
        System.out.println(simpleName + " .getClass().equals(Derived.class)) " + (x.getClass().equals(Derived.class)));
    }

    public static void main(String[] args) {
        test(new Base());
        test(new Derived());
    }
}

