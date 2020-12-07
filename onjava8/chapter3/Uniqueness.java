package chapter3;

public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique = true);
}
