package com.example.onjava8.chapter21;

// enums/Competitor.java
// Switching one enum on another
public interface Competitor<T extends Competitor<T>> {
    Outcome compete(T competitor);
}

