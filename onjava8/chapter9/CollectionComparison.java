package chapter9;

// arrays/CollectionComparison.java
// (c)2017 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.

import chapter8.Suppliers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chapter9.ArrayShow.show;

class BerylliumSphere {
    private static long counter;
    private final long id = counter++;
    @Override
    public String toString() {
        return "Sphere " + id;
    }
}

public class CollectionComparison {
    public static void main(String[] args) {
        int[] s = new int[]{1,2,3};
        int[] s2 = new int[3];
        int[] s3 = {1,2,3};

        BerylliumSphere[] spheres =new BerylliumSphere[10];
        for(int i = 0; i < 5; i++)
            spheres[i] = new BerylliumSphere();
        show(spheres);
        System.out.println(spheres[4]);
        List<BerylliumSphere> sphereList = Suppliers.create(
                ArrayList::new, BerylliumSphere::new, 5);
        System.out.println(sphereList);
        System.out.println(sphereList.get(4));

        int[] integers = { 0, 1, 2, 3, 4, 5 };
        show(integers);
        System.out.println(integers[4]);

        List<Integer> intList = new ArrayList<>(
                Arrays.asList(0, 1, 2, 3, 4, 5));
        intList.add(97);
        System.out.println(intList);
        System.out.println(intList.get(4));
    }

}


