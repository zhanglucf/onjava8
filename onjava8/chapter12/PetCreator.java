package chapter12;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public abstract class PetCreator implements Supplier<Pet> {
    private Random rand = new Random(47);

    // The List of the different types of Pet to create:
    //任何从 Pet 导出的类型
    public abstract List<Class<? extends Pet>> types();

    public Pet get() { // Create one random Pet
        int n = rand.nextInt(types().size());
        try {
            return types().get(n).newInstance();
        } catch (InstantiationException |IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
