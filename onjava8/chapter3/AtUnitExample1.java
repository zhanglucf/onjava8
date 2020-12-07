package com.example.onjava8.chapter3;

//import com.example.onjava8.atunit.Test;


import com.example.onjava8.atunit.Test;

// annotations/AtUnitExample1.java
// {java onjava.atunit.AtUnit
// build/classes/main/annotations/AtUnitExample1.class}
public class AtUnitExample1 {
    public String methodOne() {
        return "This is methodOne";
    }
    public int methodTwo() {
        System.out.println("This is methodTwo");
        return 2;
    }
    @Test
    boolean methodOneTest() {
        return methodOne().equals("This is methodOne");
    }
    @Test
    boolean m2() { return methodTwo() == 2; }
    @Test
    private boolean m3() { return true; }
    // Shows output for failure:
    @Test
    boolean failureTest() { return false; }
    @Test
    boolean anotherDisappointment() {
        return false;
    }
}

