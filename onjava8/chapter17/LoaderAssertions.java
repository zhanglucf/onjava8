package chapter17;

// validating/LoaderAssertions.java
// Using the class loader to enable assertions
// {ThrowsException}
public class LoaderAssertions {
    static {
        boolean assertionsEnabled = false;
        // Note intentional side effect of assignment:
        assert assertionsEnabled = true;
        if(!assertionsEnabled)
            throw new RuntimeException("Assertions disabled");
    }

    public static void main(String[] args) {
//        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        new Loaded().go();
    }
}

class Loaded {
    public void go() {
        assert false: "Loaded.go()";
    }
}

