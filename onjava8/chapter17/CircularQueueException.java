package com.example.onjava8.chapter17;

// validating/CircularQueueException.java
public class CircularQueueException extends RuntimeException {
    public CircularQueueException(String why) {
        super(why);
    }
}

