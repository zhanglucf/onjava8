package com.example.onjava8.chapter16;

// exceptions/Rethrowing.java
// Demonstrating fillInStackTrace()
public class Rethrowing {
    public static void f() throws Exception {
        System.out.println("originating the exception in f()");
        throw new Exception("thrown from f()");
    }
    public static void g() throws Exception {
        try {
            f();
        } catch(Exception e) {
            System.out.println("Inside g(), e.printStackTrace()");
            e.printStackTrace(System.out);
            throw e;
        }
    }
    public static void h() throws Exception {
        try {
            f();
        } catch(Exception e) {
            System.out.println("Inside h(), e.printStackTrace()");
            e.printStackTrace(System.out);
            throw (Exception)e.fillInStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            g();
        } catch(Exception e) {
            System.err.println("main: printStackTrace()");
            e.printStackTrace(System.err);
        }
        try {
            h();
        } catch(Exception e) {
            System.err.println("main: printStackTrace()");
            e.printStackTrace(System.err);
        }
    }
}

