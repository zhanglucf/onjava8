package com.example.onjava8.concurrent;

// concurrent/Baked.java

import java.util.concurrent.*;
import java.util.stream.*;

public class Baked {
    static class Pan {
    }

    static Pan pan(Batter b) {
        new Nap(1);
        return new Pan();
    }

    static Baked heat(Pan p) {
        new Nap(1);
        return new Baked();
    }

    static CompletableFuture<Baked> bake(CompletableFuture<Batter> cfb) {
        return cfb
                .thenApplyAsync(Baked::pan)
                .thenApplyAsync(Baked::heat);
    }

    public static Stream<CompletableFuture<Baked>> batch() {
        CompletableFuture<Batter> batter = Batter.mix();
        return Stream.of(
                bake(batter),
                bake(batter),
                bake(batter),
                bake(batter)
        );
    }
}

