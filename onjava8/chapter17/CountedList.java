package chapter17;

import java.util.ArrayList;

public class CountedList extends ArrayList<String> {
    private static int counter = 0;
    private int id = counter++;

    public CountedList() {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CountedList{" +
                "id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }
}
