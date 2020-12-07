package chapter21;

// enums/EnumMaps.java
// Basics of EnumMaps
// {java enums.EnumMaps}
import java.util.*;

import static chapter21.AlarmPoints.*;

interface Command { void action(); }
public class EnumMaps {
    public static void main(String[] args) {
        EnumMap<AlarmPoints,Command> em = new EnumMap<>(AlarmPoints.class);
        em.put(KITCHEN,() -> System.out.println("Kitchen fire!"));
        em.put(BATHROOM,() -> System.out.println("Bathroom alert!"));
        for(Map.Entry<AlarmPoints,Command> e:em.entrySet()) {
            System.out.print(e.getKey() + ": ");
            e.getValue().action();
        }
        try { // If there's no value for a particular key:
            em.get(UTILITY).action();
        } catch(Exception e) {
            System.out.println("Expected: " + e);
        }
    }
}

