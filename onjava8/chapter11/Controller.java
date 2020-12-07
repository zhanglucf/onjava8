package chapter11;

// innerclasses/controller/Controller.java
// The reusable framework for control systems
import java.util.*;
public class Controller {
    // A class from java.util to hold Event objects:
    private List<Event> eventList = new ArrayList<>();
    public void addEvent(Event c) { eventList.add(c); }
    public void run() throws InterruptedException {
        while(eventList.size() > 0) {
//            Thread.sleep(100);
            // Make a copy so you're not modifying the list
            // while you're selecting the elements in it:
            Thread.sleep(10);
            for (Event e : new ArrayList<>(eventList))
                if (e.ready()) {
                    System.out.println(e);
                    e.action();
                    eventList.remove(e);
                }
        }
    }
}

