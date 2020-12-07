package com.example.onjava8.concurrent;

// concurrent/FrostedCake.java

import java.util.concurrent.*;

final class Frosting {
    private Frosting() {
    }

    static CompletableFuture<Frosting> make() {
        System.out.println("糖化");
        new Nap(1);
        return CompletableFuture
                .completedFuture(new Frosting());
    }
}

public class FrostedCake {
    public FrostedCake(Baked baked, Frosting frosting) {
        new Nap(1);
    }

    @Override
    public String toString() {
        return "FrostedCake";
    }

    public static void main(String[] args) {
        Baked.batch().forEach(
                baked -> baked
                        .thenCombineAsync(Frosting.make(),
                                (cake, frosting) ->
                                        new FrostedCake(cake, frosting))
                        .thenAcceptAsync(System.out::println)
                        .join());
    }
}

