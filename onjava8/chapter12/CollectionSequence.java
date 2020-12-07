package com.example.onjava8.chapter12;

// collections/CollectionSequence.java
import java.util.*;

public class CollectionSequence extends AbstractCollection<Pet> {
    private Pet[] pets = Pets.array(8);
    @Override
    public int size() { return pets.length; }
    @Override
    public Iterator<Pet> iterator() {
        return new Iterator<Pet>() { // [1]
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < pets.length;
            }
            @Override
            public Pet next() { return pets[index++]; }
            @Override
            public void remove() { // Not implemented
                throw new UnsupportedOperationException();
            }
        };
    }
    public static void main(String[] args) {
        CollectionSequence c = new CollectionSequence();
        InterfaceVsIterator.display(c);
        InterfaceVsIterator.display(c.iterator());
    }
}
