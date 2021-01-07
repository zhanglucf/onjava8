package concurrent.jvm.atomic;

import java.util.Arrays;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 16:32
 */
public class CircularSet_8 {
    private int[] array;
    private int size;
    private int index = 0;
    public CircularSet_8(int size) {
        this.size = size;
        array = new int[size];
        // Initialize to a value not produced
        // by SerialNumbers:
        Arrays.fill(array, -1);
    }
    public synchronized void add(int i) {
        array[index] = i;
        // Wrap index and write over old elements:
        index = ++index % size;
    }
    public synchronized boolean contains(int val) {
        for(int i = 0; i < size; i++)
            if(array[i] == val) return true;
        return false;
    }
}
