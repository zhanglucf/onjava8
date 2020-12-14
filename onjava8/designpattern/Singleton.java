package designpattern;

import concurrent.Nap;
import util.Print;
import util.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static util.Print.printlnTN;

interface Resource {

    Resource add();

    int get();

}

public class Singleton {

    private static class ResourceImpl implements Resource {

        private AtomicInteger atomicInteger;

        private ResourceImpl(int i) {
            atomicInteger = new AtomicInteger(i);
        }


        @Override
        public Resource add() {
//            Print.printlnTN("add");
            new Nap(100);
            atomicInteger.addAndGet(1);
            return this;
//            new Nap(10);
        }

        @Override
        public int get() {
            return atomicInteger.get();
        }

    }

    public static Resource getResource() {
        return ResourceHolder.resource;
    }

    static class ResourceHolder {
        public static final Resource resource = new ResourceImpl(0);
    }

}

class T {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<CompletableFuture<Resource>> cfs = IntStream.range(0, 10_000)
                .mapToObj(x -> CompletableFuture.supplyAsync(Singleton::getResource))
                .collect(Collectors.toList());

        List<Resource> rs = cfs.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        System.out.println(rs.get(rs.size() - 1).get());

        ExecutorService executor = Executors.newFixedThreadPool(1_000);

        List<CompletableFuture<Resource>> cs = rs.stream()
                .map(x -> CompletableFuture.supplyAsync(x::add, executor))
                .collect(Collectors.toList());

        CompletableFuture
                .allOf(cs.toArray(new CompletableFuture[cs.size()]))
                .join();

        System.out.println(rs.get(rs.size() - 1).get());
        printlnTN("耗时", timer.duration());
        executor.shutdown();
    }

}

