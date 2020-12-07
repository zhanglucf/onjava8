package chapter21;

// enums/EnumClass.java
// Capabilities of the Enum class
enum Shrubbery {
    GROUND, CRAWLING, HANGING
}

public class EnumClass {
    public static void main(String[] args) {
        for (Shrubbery s : Shrubbery.values()) {
            System.out.println(s.name() + ".ordinal: " + s.ordinal());
            System.out.println(s.name() + ".compareTo(CRAWLING)=" + (s.compareTo(Shrubbery.CRAWLING) + " "));
            System.out.println(s.name() + ".equals(CRAWLING)=" + s.equals(Shrubbery.CRAWLING) + " ");
            System.out.println(s.name() + " == CRAWLING = " + (s == Shrubbery.CRAWLING));
            System.out.println(s.name() + ".getDeclaringClass() = " + s.getDeclaringClass());
            System.out.println(s.name() + ".name() = " + s.name());
            System.out.println("********************");
        }
// Produce an enum value from a String name:
        for (String s : "HANGING CRAWLING GROUND".split(" ")) {
            Shrubbery shrub = Enum.valueOf(Shrubbery.class, s);
            System.out.println("Enum.valueOf(" + s + ") = " + shrub);
        }
    }
}

