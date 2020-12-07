package com.example.onjava8.chapter16;
class MyException extends Exception{
    MyException(){};
    MyException(String msg){};
}
public class FullConstructors {
    public static void main(String[] args) {

        try {
            f();
        } catch (MyException e) {
            e.printStackTrace();
        }

        try {
            g();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    static void f() throws MyException {
        throw new MyException();
    }
    static void g() throws MyException {
        throw new MyException("Originated in g()");
    }
}
