package designpattern;

import concurrent.Nap;
import util.Print;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// patterns/shapes/Shape.java
public class Shape {
    private static AtomicInteger counter = new AtomicInteger(0);
    private int id = counter.addAndGet(1);

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + id + "]";
    }

    public void draw() {
        System.out.println(this + " draw");
    }

    public void erase() {
        System.out.println(this + " erase");
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1_000);

        List<CompletableFuture<Shape>> cfs =
                IntStream.range(0, 1_000)
                        .mapToObj(x -> CompletableFuture.supplyAsync(Shape::new, executor))
                        .collect(Collectors.toList());

        CompletableFuture
                .allOf(cfs.toArray(new CompletableFuture[cfs.size()]))
                .join();

        List<Shape> shapeList = cfs.stream()
                .map(CompletableFuture::join)
                .sorted(Comparator.comparingInt(x -> x.id))
                .collect(Collectors.toList());

        System.out.println(shapeList.get(shapeList.size() - 1));

        executor.shutdown();
    }
}

